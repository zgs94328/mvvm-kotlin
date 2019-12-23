package com.gs.sdk.http.interceptor

import com.orhanobut.logger.Logger
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException

class LogInterceptor : Interceptor {
    var LOGGER_NET_TAG = "request"
    override fun intercept(chain: Interceptor.Chain?): Response {
        val orgRequest = chain!!.request()
        val response = chain.proceed(orgRequest)
        val body = orgRequest.body()
        val sb = StringBuilder()
        if (orgRequest.method() == "POST" && body is FormBody) {
            val body1 = body
            for (i in 0 .. body1.size()) {
                sb.append(body1.encodedName(i) + "=" + body1.encodedValue(i) + ",")
            }
            sb.delete(sb.length - 1, sb.length)
            //打印post请求的信息
            Logger.t(LOGGER_NET_TAG).d("code=" + response.code() + "|method=" + orgRequest.method() + "|url=" + orgRequest.url()
                    + "\n" + "headers:" + orgRequest.headers().toMultimap()
                    + "\n" + "post请求体:{" + sb.toString() + "}")
        } else {
            //打印get请求的信息
            Logger.t(LOGGER_NET_TAG).d("code=" + response.code() + "|method=" + orgRequest.method() + "|url=" + orgRequest.url()
                    + "\n" + "headers:" + orgRequest.headers().toMultimap())
        }
        //返回json
        val responseBody = response.body()
        val contentLength = responseBody!!.contentLength()
        val source = responseBody.source()
        source.request(java.lang.Long.MAX_VALUE)
        val buffer = source.buffer()
        var charset = Charset.forName("utf8")
        val contentType = responseBody.contentType()
        if (contentType != null) {
            try {
                charset = contentType.charset(charset)
            } catch (e: UnsupportedCharsetException) {
                return response
            }

        }
        if (contentLength != 0L) {
            //打印返回json
            //json日志使用鼠标中键进行选中
            Logger.t(LOGGER_NET_TAG).json(buffer.clone().readString(charset))
        }
        return response
    }

}