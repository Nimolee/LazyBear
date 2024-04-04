package app.lazybear.advice

import app.lazybear.advice.screens.advice.AdviceViewModel
import app.lazybear.advice.screens.advice.AdviceViewModelImpl
import app.lazybear.advice.screens.preferences.PreferencesViewModel
import app.lazybear.advice.screens.preferences.PreferencesViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun adviceModule() = module {
    viewModel<AdviceViewModel> { AdviceViewModelImpl(get(), get()) }
    viewModel<PreferencesViewModel> { PreferencesViewModelImpl(get(), get()) }
}