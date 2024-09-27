package app.lazybear.module.ui.filters

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import app.lazybear.module.ui.components.animations.slideFromBottomAnimation
import app.lazybear.module.ui.components.animations.slideToBottomAnimation
import app.lazybear.module.ui.filters.screens.filters.FiltersArguments
import app.lazybear.module.ui.filters.screens.filters.FiltersNavigator
import app.lazybear.module.ui.filters.screens.filters.FiltersScreen

fun NavGraphBuilder.filtersNavigation(
    route: String,
    navController: NavHostController,
    onClose: (shuffle: Boolean) -> Unit,
) {
    navigation(
        route = route,
        startDestination = FiltersArguments().toRoute(),
    ) {
        composable(
            route = FiltersArguments.route,
            arguments = FiltersArguments.arguments,
            enterTransition = { slideFromBottomAnimation() },
            exitTransition = { slideToBottomAnimation() }
        ) {
            FiltersScreen(
                arguments = FiltersArguments.fromBackStack(it),
                navigator = object : FiltersNavigator {
                    override fun close(shuffle: Boolean) {
                        onClose(shuffle)
                    }
                },
            )
        }
    }
}