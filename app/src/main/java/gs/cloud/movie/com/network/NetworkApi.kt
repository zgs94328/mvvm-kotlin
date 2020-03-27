package gs.cloud.movie.com.network

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.gs.sdk.base.BaseApplication
import com.gs.sdk.network.interceptor.BaseNetworkApi
import com.gs.sdk.network.interceptor.CacheInterceptor
import gs.cloud.movie.com.model.api.NetApiService
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File

/**
 *  @author 张国胜
 *  @time 2020/3/24
 *  @desc:
 */
object NetworkApi : BaseNetworkApi() {
    //封装NetApiService变量 方便直接快速调用
    val service: NetApiService by lazy {
        getApi(NetApiService::class.java,NetApiService.SERVER_URL)
    }

    //缓存信息配置
    private val cache: Cache by lazy {
        //设置缓存路径
        val httpCacheDirectory = File(BaseApplication.app.cacheDir, "http_response")
        Cache(httpCacheDirectory, 10 * 1024 * 1024)

    }
    //Cookies自动持久化 调用 clear 可清空Cookies
    private val cookieJar: PersistentCookieJar by lazy {
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(BaseApplication.app))
    }
    override fun setHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.apply {
            cache(cache)
            cookieJar(cookieJar)
            addInterceptor(CacheInterceptor())
        }
        return builder
    }
}