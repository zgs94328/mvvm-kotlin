package gs.cloud.movie.com.app

import com.gs.sdk.http.HttpUtils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.BuildConfig
import com.orhanobut.logger.Logger
import com.gs.sdk.base.BaseApplication


class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        HttpUtils.init(this)
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}