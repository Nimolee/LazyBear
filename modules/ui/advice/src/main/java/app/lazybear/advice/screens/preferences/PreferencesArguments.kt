package app.lazybear.advice.screens.preferences

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry

class PreferencesArguments {
    companion object {
        private const val ROUTE_NAME = "preferences"

        const val route = ROUTE_NAME
        val arguments: List<NamedNavArgument> = listOf()

        fun fromBackStack(backStack: NavBackStackEntry): PreferencesArguments {
            return PreferencesArguments()
        }
    }

    fun toRoute(): String {
        return ROUTE_NAME
    }
}