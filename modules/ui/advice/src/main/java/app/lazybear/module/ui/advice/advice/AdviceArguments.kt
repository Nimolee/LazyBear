package app.lazybear.module.ui.advice.advice

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry

class AdviceArguments {
    companion object {
        private const val ROUTE_NAME = "advice"

        const val route = ROUTE_NAME
        val arguments: List<NamedNavArgument> = listOf()

        fun fromBackStack(backStack: NavBackStackEntry): AdviceArguments {
            return AdviceArguments()
        }
    }

    fun toRoute(): String {
        return ROUTE_NAME
    }
}