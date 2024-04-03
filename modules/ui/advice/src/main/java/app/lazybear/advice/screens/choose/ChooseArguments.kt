package app.lazybear.advice.screens.choose

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry

class ChooseArguments {
    companion object {
        private const val ROUTE_NAME = "choose"

        const val route = ROUTE_NAME
        val arguments: List<NamedNavArgument> = listOf()

        fun fromBackStack(backStack: NavBackStackEntry): ChooseArguments {
            return ChooseArguments()
        }
    }

    fun toRoute(): String {
        return ROUTE_NAME
    }
}