package me.hgj.jetpackmvvm.network

import android.util.MalformedJsonException
import com.google.gson.JsonParseException
import com.gs.sdk.network.ErrorEnum
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.text.ParseException


/**
 * 作者　: hegaojian
 * 时间　: 2019/12/17
 * 描述　: 根据异常返回相关的错误信息工具类
 */
object ExceptionHandle {

    fun handleException(e: Throwable?): AppException {
        val ex: AppException
        e?.let {
            when (it) {
                is HttpException -> {
                    ex = AppException(ErrorEnum.NETWORD_ERROR)
                    return ex
                }
                is JsonParseException, is JSONException, is ParseException, is MalformedJsonException -> {
                    ex = AppException(ErrorEnum.PARSE_ERROR)
                    return ex
                }
                is ConnectException -> {
                    ex = AppException(ErrorEnum.NETWORD_ERROR)
                    return ex
                }
                is javax.net.ssl.SSLException -> {
                    ex = AppException(ErrorEnum.SSL_ERROR)
                    return ex
                }
                is ConnectTimeoutException -> {
                    ex = AppException(ErrorEnum.TIMEOUT_ERROR)
                    return ex
                }
                is java.net.SocketTimeoutException -> {
                    ex = AppException(ErrorEnum.TIMEOUT_ERROR)
                    return ex
                }
                is java.net.UnknownHostException -> {
                    ex = AppException(ErrorEnum.TIMEOUT_ERROR)
                    return ex
                }
                is AppException -> return it

                else -> {
                    ex = AppException(ErrorEnum.UNKNOWN)
                    return ex
                }
            }
        }
        ex = AppException(ErrorEnum.UNKNOWN)
        return ex
    }
}