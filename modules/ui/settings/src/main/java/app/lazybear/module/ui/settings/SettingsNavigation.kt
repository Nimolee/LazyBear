package app.lazybear.module.ui.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import app.lazybear.module.ui.settings.screens.settings.SettingsArguments
import app.lazybear.module.ui.settings.screens.settings.SettingsScreen

fun NavGraphBuilder.settingsNavigation(
    route: String,
    navController: NavHostController,
) {
    navigation(
        route = route,
        startDestination = SettingsArguments().toRoute(),
    ) {
        composable(
            route = SettingsArguments.route,
            arguments = SettingsArguments.arguments,
        ) {
            SettingsScreen(
                arguments = SettingsArguments.fromBackStack(it),
                onClose = {
                    navController.popBackStack(route, inclusive = true)
                },
            )
        }
    }
}