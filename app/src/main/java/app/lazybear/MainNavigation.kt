package app.lazybear

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import app.lazybear.module.ui.advice.adviceNavigation
import app.lazybear.module.ui.navigation.NavResult
import app.lazybear.module.ui.filters.filtersNavigation

private const val ADVICE_ROUTE = "advice_module"
private const val FILTERS_ROUTE = "filters_module"

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val filtersNavResult = NavResult<Boolean>()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ADVICE_ROUTE,
    ) {
        adviceNavigation(
            route = ADVICE_ROUTE,
            navController = navController,
            onFiltersOpen = { navController.navigate(FILTERS_ROUTE) },
            onSettingsOpen = { },
            filtersNavResult = filtersNavResult,
        )
        filtersNavigation(
            route = FILTERS_ROUTE,
            navController = navController,
            onClose = { shuffle ->
                navController.popBackStack(FILTERS_ROUTE, inclusive = true)
                filtersNavResult.trySend(shuffle)
            }
        )
    }
}