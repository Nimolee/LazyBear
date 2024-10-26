package app.lazybear.module.ui.setting

import app.lazybear.module.ui.setting.screens.settings.SettingsViewModel
import app.lazybear.module.ui.setting.screens.settings.SettingsViewModelImpl
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun settingsModule() = module {
    viewModel<SettingsViewModel> { SettingsViewModelImpl() }
}