package app.lazybear.advice

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import app.lazybear.advice.screens.advice.AdviceArguments
import app.lazybear.advice.screens.advice.AdviceScreen
import app.lazybear.advice.screens.choose.ChooseScreen
import app.lazybear.advice.screens.choose.PreferencesArguments

fun NavGraphBuilder.adviceNavigation(
    route: String,
    navController: NavHostController,
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
                onPreferencesOpen = {
                    navController.navigate(PreferencesArguments().toRoute())
                }
            )
        }
        composable(
            route = PreferencesArguments.route,
            arguments = PreferencesArguments.arguments,
        ) {
            ChooseScreen(
                arguments = PreferencesArguments.fromBackStack(it)
            )
        }
    }
}