package app.lazybear.module.ui.settings.screens.settings

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry

class SettingsArguments {
    companion object {
        private const val ROUTE_NAME = "settings"

        const val route = ROUTE_NAME
        val arguments: List<NamedNavArgument> = listOf()

        fun fromBackStack(backStack: NavBackStackEntry): SettingsArguments {
            return SettingsArguments()
        }
    }

    fun toRoute(): String {
        return ROUTE_NAME
    }
}