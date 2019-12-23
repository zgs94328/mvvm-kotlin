package com.gs.sdk.base

import android.app.Application
import androidx.multidex.MultiDexApplication

open class BaseApplication : MultiDexApplication() {

    companion object  {
        @JvmStatic  lateinit var app : BaseApplication
    }
    override fun onCreate() {
        super.onCreate()
        app = this
    }
}