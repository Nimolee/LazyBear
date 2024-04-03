package app.lazybear.advice

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import app.lazybear.advice.screens.advice.AdviceArguments
import app.lazybear.advice.screens.advice.AdviceScreen
import app.lazybear.advice.screens.choose.ChooseArguments
import app.lazybear.advice.screens.choose.ChooseScreen

fun NavGraphBuilder.adviceNavigation(
    route: String,
    navController: NavHostController,
) {
    navigation(
        route = route,
        startDestination = ChooseArguments().toRoute(),
    ) {
        composable(
            route = AdviceArguments.route,
            arguments = AdviceArguments.arguments,
        ) {
            AdviceScreen(
                arguments = AdviceArguments.fromBackStack(it)
            )
        }
        composable(
            route = ChooseArguments.route,
            arguments = ChooseArguments.arguments,
        ) {
            ChooseScreen(
                arguments = ChooseArguments.fromBackStack(it)
            )
        }
    }
}