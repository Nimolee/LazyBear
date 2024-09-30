package app.lazybear.module.ui.advice

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import app.lazybear.module.ui.advice.screens.advice.AdviceArguments
import app.lazybear.module.ui.advice.screens.advice.AdviceNavigator
import app.lazybear.module.ui.advice.screens.advice.AdviceScreen
import app.lazybear.module.ui.navigation.NavResult

fun NavGraphBuilder.adviceNavigation(
    route: String,
    navController: NavHostController,
    onFiltersOpen: () -> Unit,
    onSettingsOpen: () -> Unit,
    filtersNavResult: NavResult<Boolean>,
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
                        get() = filtersNavResult

                    override fun openFilters() {
                        onFiltersOpen()
                    }

                    override fun openSettings() {
                        onSettingsOpen()
                    }
                },
            )
        }
    }
}