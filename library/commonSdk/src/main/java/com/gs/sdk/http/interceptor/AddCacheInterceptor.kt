package com.gs.sdk.http.interceptor

import android.content.Context
import com.gs.sdk.http.HttpUtils.context
import com.gs.sdk.utils.NetworkUtil
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

/**
 *  @author 张国胜
 *  @time 2019/8/21
 *  @desc:
 */
class AddCacheInterceptor(context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val cacheBuilder = CacheControl.Builder()
        cacheBuilder.maxAge(0, TimeUnit.SECONDS)
        cacheBuilder.maxStale(365, TimeUnit.DAYS)
        val cacheControl = cacheBuilder.build()
        var request = chain.request()
        if (!NetworkUtil.isNetworkConnected(context)) {
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build()
        }
        val originalResponse = chain.proceed(request)
        if (NetworkUtil.isNetworkConnected(context)) {
            // read from cache
            val maxAge = 0
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public ,max-age=$maxAge")
                    .build()
        } else {
            // tolerate 4-weeks stale
            val maxStale = 60 * 60 * 24 * 28
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .build()
        }
    }
}