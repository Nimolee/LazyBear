package app.lazybear.module.ui.setting.screens.settings

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    arguments: SettingsArguments,
    navigator: SettingsNavigator,
    viewModel: SettingsViewModel = koinViewModel(),
) {
}