package app.lazybear.module.ui.advice

import app.lazybear.module.ui.advice.advice.AdviceViewModel
import app.lazybear.module.ui.advice.advice.AdviceViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun adviceModule() = module {
    viewModel<AdviceViewModel> { AdviceViewModelImpl(get(), get()) }
}