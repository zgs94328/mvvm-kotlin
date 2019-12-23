package com.gs.sdk.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager

/**
 * 用于判断是不是联网状态
 *
 * @author Dzy
 */
object  NetworkUtil {

    /**
     * 判断网络是否连通
     */
    fun isNetworkConnected(context: Context?): Boolean {
        try {
            if (context != null) {
                val cm = context
                        .getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
                val info = cm.activeNetworkInfo
                return info != null && info.isConnected
            } else {
                /**如果context为空，就返回false，表示网络未连接 */
                return false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }


    }

    fun isWifiConnected(context: Context?): Boolean {
        if (context != null) {
            val cm = context
                    .getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = cm.activeNetworkInfo
            return info != null && info.type == ConnectivityManager.TYPE_WIFI
        } else {
            /**如果context为null就表示为未连接 */
            return false
        }

    }
}