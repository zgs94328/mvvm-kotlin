package com.gs.sdk.http.interceptor

import android.content.Context
import android.text.TextUtils
import com.gs.sdk.http.HttpUtils.context
import com.gs.sdk.http.HttpUtils.init
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*
import java.util.stream.Collectors

/**
 *  @author 张国胜
 *  @time 2019/8/21
 *  @desc:
 */

class ReceivedCookiesInterceptor(context: Context) : Interceptor {
    var context: Context

    init {
        this.context = context
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalResponse = chain.proceed(chain.request())
        //这里获取请求返回的cookie
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {

            val d = originalResponse.headers("Set-Cookie")
            //                Log.e("jing", "------------得到的 cookies:" + d.toString());

            // 返回cookie
            if (!TextUtils.isEmpty(d.toString())) {

                val sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE)
                val editorConfig = sharedPreferences.edit()
                val oldCookie: String = sharedPreferences.getString("cookie", "")

                val stringStringHashMap = HashMap<String, String>()

                // 之前存过cookie
                if (!TextUtils.isEmpty(oldCookie)) {

                    val substring = oldCookie.split(";".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                    for (aSubstring in substring) {
                        if (aSubstring.contains("=")) {
                            val split = aSubstring.split("=".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                            stringStringHashMap[split[0]] = split[1]
                        } else {
                            stringStringHashMap[aSubstring] = ""
                        }
                    }
                }
                val join = arrayOf(";").joinToString(d.toString())
                val split = join.split(";".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()

                // 存到Map里
                for (aSplit in split) {
                    val split1 = aSplit.split("=".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                    if (split1.size == 2) {
                        stringStringHashMap[split1[0]] = split1[1]
                    } else {
                        stringStringHashMap[split1[0]] = ""
                    }
                }

                // 取出来
                val stringBuilder = StringBuilder()
                if (stringStringHashMap.size > 0) {
                    for (key in stringStringHashMap.keys) {
                        stringBuilder.append(key)
                        val value = stringStringHashMap[key]
                        if (!TextUtils.isEmpty(value)) {
                            stringBuilder.append("=")
                            stringBuilder.append(value)
                        }
                        stringBuilder.append(";")
                    }
                }

                editorConfig.putString("cookie", stringBuilder.toString())
                editorConfig.apply()
                //                    Log.e("jing", "------------处理后的 cookies:" + stringBuilder.toString());
            }
        }

        return originalResponse
    }

}