package app.lazybear.advice

import app.lazybear.advice.screens.advice.AdviceViewModel
import app.lazybear.advice.screens.advice.AdviceViewModelImpl
import app.lazybear.advice.screens.choose.ChooseViewModel
import app.lazybear.advice.screens.choose.ChooseViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun adviceModule() = module {
    viewModel<AdviceViewModel> { AdviceViewModelImpl() }
    viewModel<ChooseViewModel> { ChooseViewModelImpl(get(), get()) }
}