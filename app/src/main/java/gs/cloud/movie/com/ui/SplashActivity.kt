package gs.cloud.movie.com.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gs.sdk.utils.IntentUtil
import gs.cloud.movie.com.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import org.reactivestreams.Subscription
import java.util.concurrent.TimeUnit

/**
 *  @author 张国胜
 *  @time 2019/9/30
 *  @desc:
 */
class SplashActivity : AppCompatActivity() {
    var subscribe : Disposable? = Observable.timer(2, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                IntentUtil.get().goActivity(this@SplashActivity,MainActivity::class.java)
                overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out)
                finish()
            }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 后台返回时可能启动这个页面 http://blog.csdn.net/jianiuqi/article/details/54091181
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT !== 0) {
            finish()
            return
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        subscribe?.dispose()
    }
}