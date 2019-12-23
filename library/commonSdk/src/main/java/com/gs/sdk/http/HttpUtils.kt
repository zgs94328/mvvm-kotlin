package com.gs.sdk.http

import android.annotation.SuppressLint
import android.content.Context
import com.gs.sdk.BuildConfig
import com.gs.sdk.http.interceptor.*
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.File
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

@SuppressLint("StaticFieldLeak")
object HttpUtils {

    lateinit var context: Context

    object StaticUrl{
        val API_GANKIO = "https://gank.io/api/"
        val API_DOUBAN = "Https://api.douban.com/"
        val API_TING = "https://tingapi.ting.baidu.com/v1/restserver/"
        val API_FIR = "http://api.fir.im/apps/"
        val API_WAN_ANDROID = "https://www.wanandroid.com/"
        val API_QSBK = "http://m2.qiushibaike.com/"
        val API_MTIME = "https://api-m.mtime.cn/"
        val API_MTIME_TICKET = "https://ticket-api-m.mtime.cn/"
    }

    /**
     *
     */
    fun init(context: Context) {
        this.context = context
    }


    fun getBuilder(url:String):Retrofit.Builder{
        var builder = Retrofit.Builder()
        builder.client(getHttpClient())
        return builder
    }
    fun getHttpClient(): OkHttpClient{
        var client = getUnsafeOkHttpClient()
        return client
    }

    fun getUnsafeOkHttpClient(): OkHttpClient {
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustAllCerts, SecureRandom())
        //cache url
        val httpCacheDirectory = File(context.cacheDir, "responses")
        // 50 MiB
        val cacheSize = 50 * 1024 * 1024
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory
        var okHttpClient = OkHttpClient.Builder()
        okHttpClient.readTimeout(30,TimeUnit.SECONDS)
        okHttpClient.writeTimeout(30,TimeUnit.SECONDS)
        okHttpClient.connectTimeout(30, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(LogInterceptor())
        okHttpClient.addInterceptor(HeadInterceptor())
        // 持久化cookie
        okHttpClient.addInterceptor(ReceivedCookiesInterceptor(context))
        okHttpClient.addInterceptor(AddCookiesInterceptor(context))
        // 添加缓存，无网访问时会拿缓存,只会缓存get请求
        okHttpClient.addInterceptor(AddCacheInterceptor(context))
        okHttpClient.cache(cache)
        okHttpClient.addInterceptor(HttpLoggingInterceptor()
                .setLevel(if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else
                    HttpLoggingInterceptor.Level.NONE))
        okHttpClient.hostnameVerifier(HostnameVerifier { hostname, session -> true })
        return okHttpClient.build()
    }

    internal val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return arrayOf()
        }
    })

}