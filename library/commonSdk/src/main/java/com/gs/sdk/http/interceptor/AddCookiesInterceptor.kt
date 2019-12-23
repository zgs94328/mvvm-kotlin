package com.gs.sdk.http.interceptor

import android.content.Context
import com.gs.sdk.http.HttpUtils.context
import okhttp3.Interceptor
import okhttp3.Response

/**
 *  @author 张国胜
 *  @time 2019/8/21
 *  @desc:
 */
class AddCookiesInterceptor(context: Context):Interceptor {
    var context: Context
    init {
        this.context = context;
    }


    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE)
        val cookie = sharedPreferences.getString("cookie", "")
        builder.addHeader("Cookie", cookie!!)
        return chain.proceed(builder.build())
    }
}