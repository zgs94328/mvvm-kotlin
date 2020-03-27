package gs.cloud.movie.com.ui.login

import android.view.View
import android.widget.CompoundButton
import androidx.lifecycle.Observer
import com.gs.sdk.ext.parseState
import com.gs.sdk.utils.toast
import gs.cloud.movie.com.base.BaseActivity
import gs.cloud.movie.com.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


/**
 *  @author 张国胜
 *  @time 2020/3/18
 *  @desc:
 */
class LoginActivity : BaseActivity<LoginModel, ActivityLoginBinding>() {
    override fun initVM(): LoginModel = getViewModel()
    override fun getLayoutResId(): Int = gs.cloud.movie.com.R.layout.activity_login

    override fun initView() {
        mBinding.viewmodel = mViewModel
        mBinding.activity = this

    }

    override fun initData() {
        login_key.setOnCheckedChangeListener { _: CompoundButton?, b: Boolean ->
            mViewModel.isShowPwd.set(b)
        }
    }

    override fun createObserver() {
        mViewModel.loginResult.observe(this, Observer { viewState ->
            parseState(viewState, {
                //登录成功 通知账户数据发生改变鸟

            })
        })
    }


    fun onClick(view: View) {
        when (view.id) {
            gs.cloud.movie.com.R.id.login_clear -> {
                mViewModel.username.set("")

            }
            gs.cloud.movie.com.R.id.login_sub -> {
                when {
                    mViewModel.username.get().isEmpty() -> toast("请输入用户名")
                    mViewModel.password.get().isEmpty() -> toast("请输入密码")
                    else -> {
                        mViewModel.login()
                    }
                }
            }

        }
    }
}