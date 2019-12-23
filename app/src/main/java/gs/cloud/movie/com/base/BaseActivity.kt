package gs.cloud.movie.com.base

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProviders
import com.gs.sdk.utils.ClassUtil
import com.gs.sdk.utils.ResourceUtil
import com.gs.sdk.utils.StatusBarUtil
import gs.cloud.movie.com.R
import gs.cloud.movie.com.databinding.ActivityBaseBinding
import java.util.*


/**
 *  @author 张国胜
 *  @time 2019/8/21
 *  @desc:
 */
open class BaseActivity<VM: AndroidViewModel , SV : ViewDataBinding> : AppCompatActivity() {
    protected lateinit var viewModel: VM
    // 布局view
    protected lateinit var bindingView: SV
    private var errorView: View? = null
    private var loadingView: View? = null
    private var mBaseBinding: ActivityBaseBinding? = null
    private var mAnimationDrawable: AnimationDrawable? = null
//    private var mCompositeDisposable: CompositeDisposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        mBaseBinding =  DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base,null,false)//获取base布局
        bindingView =  DataBindingUtil.inflate(LayoutInflater.from(this),layoutResID,null,false)//获取布局
        // content
        var params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        bindingView!!.root.layoutParams = params
        var mContainer = mBaseBinding!!.root.findViewById(R.id.container) as RelativeLayout
        mContainer.addView(bindingView!!.root)
        window.setContentView(mBaseBinding!!.root)
        // 设置透明状态栏，兼容4.4
        StatusBarUtil.setColor(this,ResourceUtil.getColor(R.color.colorTheme),0)
        //加载动画
        loadingView = findViewById(R.id.vs_loading)
        var img = loadingView!!.findViewById(R.id.img_progress) as ImageView
        mAnimationDrawable = img?.drawable as AnimationDrawable
        if(!mAnimationDrawable?.isRunning!!){
            mAnimationDrawable!!.start()
        }


        bindingView.root.visibility = View.GONE
        setToolBar()
        initViewModel()
    }

    private fun setToolBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initViewModel() {
        val viewModelClass = ClassUtil.getViewModel<VM>(this)
        if (viewModelClass != null) {
            this.viewModel = ViewModelProviders.of(this).get(viewModelClass)
        }
    }

    override fun setTitle(title: CharSequence?) {
        mBaseBinding!!.toolBar.title = title
    }

    fun showLoading(){
        //显示loading布局
        if(loadingView!=null && loadingView!!.visibility != View.VISIBLE){
            loadingView!!.visibility = View.VISIBLE
        }
        //隐藏other
        if (bindingView.root.visibility !== View.GONE) {
            bindingView.root.visibility = View.GONE
        }
        if (errorView != null) {
            errorView?.visibility = View.GONE
        }
        //开始动画
        if(!mAnimationDrawable?.isRunning!!){
            mAnimationDrawable?.start()
        }
    }

    fun hideLoading(){
        if (loadingView != null && loadingView!!.visibility != View.GONE) {
            loadingView!!.visibility = View.GONE
        }
        // 停止动画
        if (mAnimationDrawable?.isRunning!!) {
            mAnimationDrawable?.stop()
        }
    }


    protected fun showContentView() {
        hideLoading()
        if (errorView != null) {
            errorView?.visibility = View.GONE
        }
        if (bindingView.root.visibility !== View.VISIBLE) {
            bindingView.root.visibility = View.VISIBLE
        }
    }

    protected fun showError() {
       hideLoading()
        if (errorView == null) {
            val viewStub = findViewById<ViewStub>(R.id.vs_error_refresh)
            errorView = viewStub.inflate()
            // 点击加载失败布局
            errorView!!.setOnClickListener(){
                showLoading()
                onRefresh()
            }

        } else {
            errorView!!.visibility = View.VISIBLE
        }
        if (bindingView.root.visibility !== View.GONE) {
            bindingView.root.visibility = View.GONE
        }
    }

    /**
     * 失败后点击刷新
     */
    fun onRefresh() {

    }

}