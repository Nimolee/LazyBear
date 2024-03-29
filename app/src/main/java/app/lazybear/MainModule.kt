package app.lazybear

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun mainModule() = module {
    viewModel<MainViewModel> { MainViewModelImpl(get()) }
}