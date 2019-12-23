package com.gs.sdk.http.interceptor

import com.gs.sdk.http.HttpUtils.context
import com.gs.sdk.utils.NetworkUtil
import okhttp3.Interceptor
import okhttp3.Response
/**
 * 用于判断是不是联网状态
 *
 * @author Dzy
 */
class HeadInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        builder.addHeader("Accept", "application/json;versions=1")
        if (NetworkUtil.isNetworkConnected(context)) {
            val maxAge = 60
            builder.addHeader("Cache-Control", "public, max-age=$maxAge")
        } else {
            val maxStale = 60 * 60 * 24 * 28
            builder.addHeader("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
        }
        return chain.proceed(builder.build())
    }
}