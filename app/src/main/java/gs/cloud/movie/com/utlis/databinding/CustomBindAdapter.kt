package gs.cloud.movie.com.utlis.databinding

import android.text.InputType
import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.orhanobut.logger.Logger

/**
 *  @author 张国胜
 *  @time 2020/3/19
 *  @desc:
 */
object CustomBindAdapter {
    @BindingAdapter("isShowPwd")
    @JvmStatic
    fun isShowPwd(view: EditText, boolean: Boolean) {
        if (boolean) {
            view.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            view.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        view.setSelection(view.text.toString().length)//让光标在最右侧
    }

    @BindingAdapter("onClick")
    @JvmStatic
    fun onClick(view: View, listener: View.OnClickListener) {
        view.clickWithTrigger {
            listener.onClick(view)
        }
    }

}

/***
 * 带延迟过滤的点击事件View扩展
 * @param delay Long 延迟时间，默认600毫秒
 * @param block: (T) -> Unit 函数
 * @return Unit
 */
fun <T : View> T.clickWithTrigger(time: Long = 600, block: (T) -> Unit) {
    triggerDelay = time
    setOnClickListener {
        if (clickEnable()) {
            block(it as T)
        }else{
            Logger.e("重复点击了！！")
        }
    }
}

private var <T : View> T.triggerLastTime: Long
    get() = if (getTag(1123460103) != null) getTag(1123460103) as Long else 0
    set(value) {
        setTag(1123460103, value)
    }

private var <T : View> T.triggerDelay: Long
    get() = if (getTag(1123461123) != null) getTag(1123461123) as Long else -1
    set(value) {
        setTag(1123461123, value)
    }

private fun <T : View> T.clickEnable(): Boolean {
    var flag = false
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - triggerLastTime >= triggerDelay) {
        flag = true
    }
    triggerLastTime = currentClickTime
    return flag
}

fun EditText.textString(): String {
    return this.text.toString()
}
