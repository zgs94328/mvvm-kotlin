package gs.cloud.movie.com.ui

/**
 *  @author 张国胜
 *  @time 2019/11/13
 *  @desc:
 */
open class BaseAc(p : Int) {
    open val a : Int = 0
        get() = field
    open fun test() {}
    fun haha(){}

}
interface BaseTest{
    fun test(){}
}