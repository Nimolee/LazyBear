package app.lazybear.module.ui.advice

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import app.lazybear.module.ui.advice.screens.advice.AdviceArguments
import app.lazybear.module.ui.advice.screens.advice.AdviceNavigator
import app.lazybear.module.ui.advice.screens.advice.AdviceScreen
import app.lazybear.module.ui.advice.screens.backdrops.BackdropArguments
import app.lazybear.module.ui.advice.screens.backdrops.BackdropNavigator
import app.lazybear.module.ui.advice.screens.backdrops.BackdropScreen
import app.lazybear.module.ui.components.animations.slideFromBottomAnimation
import app.lazybear.module.ui.components.animations.slideToBottomAnimation
import app.lazybear.module.ui.navigation.NavResult

fun NavGraphBuilder.adviceNavigation(
    route: String,
    navController: NavHostController,
    onSettingsOpen: () -> Unit,
    settingsNavResult: NavResult<Boolean>,
) {
    navigation(
        route = route,
        startDestination = AdviceArguments().toRoute(),
    ) {
        composable(
            route = AdviceArguments.route,
            arguments = AdviceArguments.arguments,
        ) {
            AdviceScreen(
                arguments = AdviceArguments.fromBackStack(it),
                navigator = object : AdviceNavigator {
                    override val settingsResult: NavResult<Boolean>
                        get() = settingsNavResult

                    override fun openSettings() {
                        onSettingsOpen()
                    }

                    override fun openBackdropGallery(movieId: Int) {
                        navController.navigate(BackdropArguments(movieId).toRoute())
                    }
                },
            )
        }
        composable(
            route = BackdropArguments.route,
            arguments = BackdropArguments.arguments,
            enterTransition = { slideFromBottomAnimation() },
            exitTransition = { slideToBottomAnimation() },
        ) {
            BackdropScreen(
                arguments = BackdropArguments.fromBackStack(it),
                navigator = object : BackdropNavigator {
                    override fun close() {
                        navController.popBackStack(BackdropArguments.route, inclusive = true)
                    }
                }
            )
        }
    }
}