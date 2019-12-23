package com.gs.sdk.utils

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.gs.sdk.base.BaseApplication

/**
 *  @author 张国胜
 *  @time 2019/8/22
 *  @desc:
 */
object ResourceUtil {
    fun getDrawable(resid:Int): Drawable {
        return ContextCompat.getDrawable(BaseApplication.app,resid)!!
    }

    fun getColor(resid:Int): Int {
        return ContextCompat.getColor(BaseApplication.app,resid)!!
    }

}