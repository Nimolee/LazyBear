package app.lazybear.module.ui.advice.screens.backdrops

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

class BackdropArguments(
    val movieId: Int,
) {
    companion object {
        private const val ROUTE_NAME = "backdrop"
        private const val MOVIE_ID_ARG = "movieId"

        const val route = "$ROUTE_NAME/{${MOVIE_ID_ARG}}"
        val arguments: List<NamedNavArgument> = listOf(
            navArgument(MOVIE_ID_ARG) { type = NavType.IntType }
        )

        fun fromBackStack(backStack: NavBackStackEntry): BackdropArguments {
            val movieId = backStack.arguments!!.getInt(MOVIE_ID_ARG)
            return BackdropArguments(
                movieId = movieId
            )
        }
    }

    fun toRoute(): String {
        return "$ROUTE_NAME/$movieId"
    }
}