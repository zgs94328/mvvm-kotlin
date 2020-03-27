package com.gs.sdk.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 *  @author 张国胜
 *  @time 2020/3/18
 *  @desc:
 */
abstract class BaseVmActivity<VM : BaseViewModel, DB : ViewDataBinding>(isDataBindingLayout: Boolean = true) : AppCompatActivity() {
    private var isBindingLayout = isDataBindingLayout
    protected lateinit var mBinding: DB//lateinit延迟初始化
    lateinit var mViewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel =initVM()
        createObserver()
        if(isBindingLayout){
            mBinding = DataBindingUtil.setContentView(this, getLayoutResId())
            mBinding.setLifecycleOwner(this)
        }else{
            setContentView(getLayoutResId())
        }
        initView()
        initData()
    }

    abstract fun getLayoutResId(): Int
    abstract fun initVM(): VM
    abstract fun initView()
    abstract fun initData()
    abstract fun createObserver()
    abstract fun showProgressDialog(message:String = "请求网络中...")
    abstract fun showErrMsg(errMsg:String = "未知错误...")
    abstract fun dismissProgressDialog()

}