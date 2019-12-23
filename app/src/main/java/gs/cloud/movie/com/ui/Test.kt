package gs.cloud.movie.com.ui

import android.content.Context
import android.widget.ImageView
import android.widget.Toast

/**
 *  @author 张国胜
 *  @time 2019/11/15
 *  @desc:
 */
fun ImageView.load(context: Context,url:String){

}
fun Context.toast(msg:String){
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}
fun String.toast(context: Context){
    Toast.makeText(context,this,Toast.LENGTH_LONG).show()
}