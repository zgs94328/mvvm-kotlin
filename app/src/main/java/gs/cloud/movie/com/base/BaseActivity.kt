package gs.cloud.movie.com.base

import android.app.ProgressDialog
import androidx.databinding.ViewDataBinding
import com.gs.sdk.base.BaseViewModel
import com.gs.sdk.base.BaseVmActivity
import com.gs.sdk.utils.toast

/**
 *  @author 张国胜
 *  @time 2020/3/26
 *  @desc:
 */
abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding>(isDataBindingLayout: Boolean = true) : BaseVmActivity<VM, DB>(isDataBindingLayout)  {

    private var progressDialog: ProgressDialog? = null
    /**
     * 打开等待框
     */
   override  fun showProgressDialog(msg:String) {
        if (progressDialog == null){
            progressDialog = ProgressDialog(this)
            progressDialog!!.setTitle(msg)
        }

        progressDialog?.show()
    }

    override fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }
    /**
     * 展示请求报错信息
     */
    override fun showErrMsg(errMsg:String){
        toast(errMsg)
    }


}