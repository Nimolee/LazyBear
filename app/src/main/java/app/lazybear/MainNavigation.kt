package app.lazybear

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import app.lazybear.module.ui.advice.adviceNavigation
import app.lazybear.module.ui.navigation.NavResult
import app.lazybear.module.ui.settings.settingsNavigation

private const val ADVICE_ROUTE = "advice_module"
private const val SETTINGS_ROUTE = "settings_module"

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val settingsNavResult = NavResult<Boolean>()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ADVICE_ROUTE,
    ) {
        adviceNavigation(
            route = ADVICE_ROUTE,
            navController = navController,
            onSettingsOpen = { navController.navigate(SETTINGS_ROUTE) },
            settingsNavResult = settingsNavResult,
        )
        settingsNavigation(
            route = SETTINGS_ROUTE,
            navController = navController,
            onClose = { shuffle ->
                navController.popBackStack(SETTINGS_ROUTE, inclusive = true)
                settingsNavResult.trySend(shuffle)
            }
        )
    }
}