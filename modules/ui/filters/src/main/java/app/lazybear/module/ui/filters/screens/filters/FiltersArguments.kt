package app.lazybear.module.ui.filters.screens.filters

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry

class FiltersArguments {
    companion object {
        private const val ROUTE_NAME = "filters"

        const val route = ROUTE_NAME
        val arguments: List<NamedNavArgument> = listOf()

        fun fromBackStack(backStack: NavBackStackEntry): FiltersArguments {
            return FiltersArguments()
        }
    }

    fun toRoute(): String {
        return ROUTE_NAME
    }
}