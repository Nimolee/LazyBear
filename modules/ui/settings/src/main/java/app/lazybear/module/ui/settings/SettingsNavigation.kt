package app.lazybear.module.ui.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import app.lazybear.module.ui.components.animations.slideFromBottomAnimation
import app.lazybear.module.ui.components.animations.slideToBottomAnimation
import app.lazybear.module.ui.settings.screens.settings.SettingsArguments
import app.lazybear.module.ui.settings.screens.settings.SettingsNavigator
import app.lazybear.module.ui.settings.screens.settings.SettingsScreen

fun NavGraphBuilder.settingsNavigation(
    route: String,
    navController: NavHostController,
    onClose: (shuffle: Boolean) -> Unit,
) {
    navigation(
        route = route,
        startDestination = SettingsArguments().toRoute(),
    ) {
        composable(
            route = SettingsArguments.route,
            arguments = SettingsArguments.arguments,
            enterTransition = { slideFromBottomAnimation() },
            exitTransition = { slideToBottomAnimation() }
        ) {
            SettingsScreen(
                arguments = SettingsArguments.fromBackStack(it),
                navigator = object : SettingsNavigator {
                    override fun close(shuffle: Boolean) {
                        onClose(shuffle)
                    }
                },
            )
        }
    }
}