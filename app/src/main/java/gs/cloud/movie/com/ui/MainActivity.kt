package gs.cloud.movie.com.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import gs.cloud.movie.com.R
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG: String = "mainactivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GlobalScope.launch{
            delay(3000)
            for (i in 1..3){
                Log.d("AA", "1111")
            }
            delay(1000)
            for (i in 1..3){
                Log.d("AA", "222")
            }
        }
        Log.d("AA", "主线程位于协程之后的代码执行，时间:  ${System.currentTimeMillis()}")

    }


}
