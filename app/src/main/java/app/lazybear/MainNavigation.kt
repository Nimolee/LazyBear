package app.lazybear

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import app.lazybear.module.ui.advice.adviceNavigation
import app.lazybear.module.ui.settings.settingsNavigation
import kotlinx.coroutines.channels.Channel

private const val ADVICE_ROUTE = "advice_module"
private const val SETTINGS_ROUTE = "settings_module"

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val preferencesResultChannel = Channel<Boolean>()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ADVICE_ROUTE,
    ) {
        adviceNavigation(
            route = ADVICE_ROUTE,
            navController = navController,
            onPreferencesOpen = {
                navController.navigate(SETTINGS_ROUTE)
                preferencesResultChannel.receive()
            }
        )
        settingsNavigation(
            route = SETTINGS_ROUTE,
            navController = navController,
            onClose = { shuffle ->
                navController.popBackStack(SETTINGS_ROUTE, inclusive = true)
                preferencesResultChannel.trySend(shuffle)
            }
        )
    }
}