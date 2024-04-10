package app.lazybear.module.ui.advice

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import app.lazybear.module.ui.advice.advice.AdviceArguments
import app.lazybear.module.ui.advice.advice.AdviceScreen
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
                onSettingsOpen = onSettingsOpen,
                settingsNavResult = settingsNavResult,
            )
        }
    }
}