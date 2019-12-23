package com.gs.sdk.utils


import android.app.Application

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel

/**
 * Created by jingbin on 2018/12/26.
 */

class ClassUtil {
    internal inner class NoViewModel(@NonNull application: Application) : AndroidViewModel(application)

    companion object {

        /**
         * 获取泛型ViewModel的class对象
         */
        fun <T> getViewModel(obj: Any): Class<T>? {
            val currentClass = obj.javaClass
            val tClass = getGenericClass<T>(currentClass, AndroidViewModel::class.java)
            return if (tClass == null || tClass == AndroidViewModel::class.java || tClass == NoViewModel::class.java) {
                null
            } else tClass
        }

        private fun <T> getGenericClass(klass: Class<*>, filterClass: Class<*>): Class<T>? {
            val type = klass.genericSuperclass
            if (type == null || type !is ParameterizedType) return null
            val types = type.actualTypeArguments
            for (t in types) {
                val tClass = t as Class<T>
                if (filterClass.isAssignableFrom(tClass)) {
                    return tClass
                }
            }
            return null
        }
    }
}
