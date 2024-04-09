package app.lazybear.module.ui.settings

import app.lazybear.module.ui.settings.screens.settings.SettingsViewModel
import app.lazybear.module.ui.settings.screens.settings.SettingsViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun settingsModule() = module {
    viewModel<SettingsViewModel> { SettingsViewModelImpl(get(), get()) }
}