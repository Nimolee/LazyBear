package app.lazybear.module.ui.advice

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import app.lazybear.module.ui.advice.advice.AdviceArguments
import app.lazybear.module.ui.advice.advice.AdviceScreen

fun NavGraphBuilder.adviceNavigation(
    route: String,
    navController: NavHostController,
    onPreferencesOpen: () -> Unit,
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
                onPreferencesOpen = onPreferencesOpen,
            )
        }
    }
}