package gs.cloud.movie.com.di
import gs.cloud.movie.com.model.repository.LoginRepository
import gs.cloud.movie.com.ui.login.LoginModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.get
import org.koin.dsl.module

/**
 * Created by luyao
 * on 2019/11/15 15:44
 */

val viewModelModule = module {
    viewModel { LoginModel(get()) }
}

val repositoryModule = module {
    single { LoginRepository() }
}

val appModule = listOf(viewModelModule, repositoryModule)