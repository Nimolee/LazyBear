package app.lazybear.module.ui.settings

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import app.lazybear.module.ui.settings.screens.settings.SettingsArguments
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
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(300, easing = EaseIn),
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(300, easing = EaseIn),
                )
            }
        ) {
            SettingsScreen(
                arguments = SettingsArguments.fromBackStack(it),
                onClose = onClose,
            )
        }
    }
}