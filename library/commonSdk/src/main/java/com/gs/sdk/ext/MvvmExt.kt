package com.gs.sdk.ext

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gs.sdk.base.BaseViewModel
import com.gs.sdk.base.BaseVmActivity
import com.gs.sdk.network.BaseResponse
import com.gs.sdk.utils.paresException
import com.gs.sdk.utils.paresResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.hgj.jetpackmvvm.network.AppException
import me.hgj.jetpackmvvm.state.ViewState

/**
 *  @author 张国胜
 *  @time 2020/3/24
 *  @desc:
 */
/**
 *
 * net request 会校验请求结果数据是否是成功
 * @param request request method
 * @param viewState request result
 * @param showLoading 配置是否显示等待框
 */
fun <T> BaseViewModel.launchRequest(
        request: suspend () -> BaseResponse<T>,
        viewState: MutableLiveData<ViewState<T>>,
        showLoading: Boolean = false,
        loadingMessage: String="请求网络中..."
) {
    viewModelScope.launch {
        runCatching {
            if (showLoading) viewState.value = ViewState.onAppLoading(loadingMessage)
            withContext(Dispatchers.IO) { request() }
        }.onSuccess {
            viewState.paresResult(it)
        }.onFailure {
            Log.i("throwable",it.message)
            viewState.paresException(it)
        }
    }
}
/**
 * 显示页面状态，这里有个技巧，成功回调在第一个，其后两个带默认值的回调可省
 * @param viewState 接口返回值
 * @param onLoading 加载中
 * @param onSuccess 成功回调
 * @param onError 失败回调
 *
 */
fun <T> BaseVmActivity<*,*>.parseState(
        viewState: ViewState<T>,
        onSuccess: (T) -> Unit,
        onError: ((AppException) -> Unit)? = null,
        onLoading: (() -> Unit)? = null
) {
    when (viewState) {
        is ViewState.Loading -> {
            showProgressDialog(viewState.loadingMessage)
            onLoading?.run { this }
        }
        is ViewState.Success -> {
            dismissProgressDialog()
            onSuccess(viewState.data)
        }
        is ViewState.Error -> {
            dismissProgressDialog()
            if(onError!=null)onError?.run { this(viewState.error) }else  showErrMsg(viewState.error.errorMsg)
        }
    }
}