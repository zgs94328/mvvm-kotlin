package gs.cloud.movie.com.ui.login

import androidx.lifecycle.MutableLiveData
import com.gs.sdk.base.BaseViewModel
import com.gs.sdk.ext.launchRequest
import gs.cloud.movie.com.model.bean.User
import gs.cloud.movie.com.model.repository.LoginRepository
import me.hgj.jetpackmvvm.databind.BooleanObservableField
import me.hgj.jetpackmvvm.databind.StringObservableField
import me.hgj.jetpackmvvm.state.ViewState

/**
 *  @author 张国胜
 *  @time 2020/3/18
 *  @desc:
 */
class LoginModel(var loginRpository: LoginRepository) : BaseViewModel() {
    var username = StringObservableField()
    var password = StringObservableField()
    var isShowPwd = BooleanObservableField()
    //过滤处理请求结果
    var loginResult = MutableLiveData<ViewState<User>>()
    //多请求合并数据
    fun login() {
//        viewModelScope.launch {
//            try {
//                loginResult.value = ViewState.onAppLoading("登录中...")
//                var userBean = withContext(Dispatchers.IO) { async { loginRpository.login(username.get(), password.get()) } }
//                loginResult.paresResult(userBean.await())
//            } catch (e: Exception) {
//                loginResult.paresException(e)
//            }
//        }
        //1.这种是回调在 Activity或Fragment中的
        launchRequest(
                { loginRpository.login(username.get(), password.get()) }//请求体
                , loginResult,//请求的返回结果，请求成功与否都会改变该值，在Activity或fragment中监听回调结果，具体可看loginActivity中的回调
                true,//是否显示等待框，，默认false不显示 可以不填
                "正在登录中..."//等待框内容，默认：请求网络中...  可以不填
        )
    }
}
