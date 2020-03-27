package gs.cloud.movie.com.model.repository

import gs.cloud.movie.com.model.api.ApiResponse
import gs.cloud.movie.com.model.bean.User
import gs.cloud.movie.com.network.NetworkApi


/**
 *  @author 张国胜
 *  @time 2020/3/18
 *  @desc:注册登录的数据仓库
 */
class LoginRepository {
    suspend fun login(username:String,password:String):ApiResponse<User>{
        return NetworkApi.service.login(username, password)
    }

}