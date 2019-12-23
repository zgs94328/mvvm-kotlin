package com.gs.sdk.utils

import android.os.Parcelable
import androidx.core.app.ActivityCompat.startActivityForResult
import android.app.Activity
import android.content.Context
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent



/**
 *  @author 张国胜
 *  @time 2019/10/8
 *  @desc:
 */
class IntentUtil {
    val OPEN_ACTIVITY_KEY = "open_activity"//intent跳转传递传输key

    companion object {
        private var manager: IntentUtil? = null
        private var intent: Intent? = null
        fun get(): IntentUtil {
            if (manager == null) {
                synchronized(IntentUtil::class.java) {
                    if (manager == null) {
                        manager = IntentUtil()
                    }
                }
            }
            intent = Intent()
            return manager!!
        }
    }


    /**
     * 获取上一个界面传递过来的参数
     *
     * @param activity this
     * @param <T>      泛型
     * @return
    </T> */
    fun <T> getParcelableExtra(activity: Activity?): T {
        var activity = activity
        val parcelable = activity!!.intent.getParcelableExtra<Parcelable>(OPEN_ACTIVITY_KEY)
        activity = null
        return parcelable as T
    }

    /**
     * 启动一个Activity
     *
     * @param _this
     * @param _class
     */
    fun goActivity(_this: Context?, _class: Class<out Activity>) {
        var _this = _this
        intent!!.setClass(_this!!, _class)
        _this!!.startActivity(intent)
        _this = null
    }

    /**
     * 启动activity后kill前一个
     *
     * @param _this
     * @param _class
     */
    fun goActivityKill(_this: Context?, _class: Class<out Activity>) {
        var _this = _this
        intent!!.setClass(_this!!, _class)
        _this!!.startActivity(intent)
        (_this as Activity).finish()
        _this = null
    }

    /**
     * 回调跳转
     *
     * @param _this
     * @param _class
     * @param requestCode
     */
    fun goActivityResult(_this: Activity?, _class: Class<out Activity>, requestCode: Int) {
        var _this = _this
        intent!!.setClass(_this!!, _class)
        _this.startActivityForResult(intent, requestCode)
        _this = null
    }

    /**
     * 回调跳转并finish当前页面
     *
     * @param _this
     * @param _class
     * @param requestCode
     */
    fun goActivityResultKill(_this: Activity?, _class: Class<out Activity>, requestCode: Int) {
        var _this = _this
        intent!!.setClass(_this!!, _class)
        _this.startActivityForResult(intent, requestCode)
        _this.finish()
        _this = null
    }

    /**
     * 传递一个序列化实体类
     *
     * @param _this
     * @param _class
     * @param parcelable
     */
    fun goActivity(_this: Context?, _class: Class<out Activity>, parcelable: Parcelable) {
        var _this = _this
        intent!!.setClass(_this!!, _class)
        putParcelable(parcelable)
        _this!!.startActivity(intent)
        _this = null
    }

    /**
     * 启动一个Activity
     *
     * @param _this
     * @param _class
     * @param flags
     * @param parcelable 传递的实体类
     */
    fun goActivity(_this: Context?, _class: Class<out Activity>, flags: Int, parcelable: Parcelable) {
        var _this = _this
        intent!!.setClass(_this!!, _class)
        setFlags(flags)
        putParcelable(parcelable)
        _this!!.startActivity(intent)
        _this = null
    }

    fun goActivityKill(_this: Context?, _class: Class<out Activity>, parcelable: Parcelable) {
        var _this = _this
        intent!!.setClass(_this!!, _class)
        putParcelable(parcelable)
        _this!!.startActivity(intent)
        (_this as Activity).finish()
        _this = null
    }

    fun goActivityKill(_this: Context?, _class: Class<out Activity>, flags: Int, parcelable: Parcelable) {
        var _this = _this
        intent!!.setClass(_this!!, _class)
        setFlags(flags)
        putParcelable(parcelable)
        _this!!.startActivity(intent)
        (_this as Activity).finish()
        _this = null
    }

    fun goActivityResult(_this: Activity?, _class: Class<out Activity>, parcelable: Parcelable, requestCode: Int) {
        var _this = _this
        intent!!.setClass(_this!!, _class)
        putParcelable(parcelable)
        _this.startActivityForResult(intent, requestCode)
        _this = null
    }

    fun goActivityResult(_this: Activity?, _class: Class<out Activity>, flags: Int, parcelable: Parcelable, requestCode: Int) {
        var _this = _this
        intent!!.setClass(_this!!, _class)
        putParcelable(parcelable)
        setFlags(flags)
        _this.startActivityForResult(intent, requestCode)
        _this = null
    }

    fun goActivityResultKill(_this: Activity?, _class: Class<out Activity>, parcelable: Parcelable, requestCode: Int) {
        var _this = _this
        intent!!.setClass(_this!!, _class)
        putParcelable(parcelable)
        _this.startActivityForResult(intent, requestCode)
        _this.finish()
        _this = null
    }

    fun goActivityResultKill(_this: Activity?, _class: Class<out Activity>, flags: Int, parcelable: Parcelable, requestCode: Int) {
        var _this = _this
        intent!!.setClass(_this!!, _class)
        putParcelable(parcelable)
        setFlags(flags)
        _this.startActivityForResult(intent, requestCode)
        _this.finish()
        _this = null
    }

    private fun setFlags(flags: Int) {
        if (flags < 0) return
        intent!!.flags = flags
    }

    private fun putParcelable(parcelable: Parcelable?) {
        if (parcelable == null) return
        intent!!.putExtra(OPEN_ACTIVITY_KEY, parcelable)
    }
}