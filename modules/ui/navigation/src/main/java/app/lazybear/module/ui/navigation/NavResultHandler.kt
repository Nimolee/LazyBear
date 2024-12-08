package app.lazybear.module.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

/**
 * Helper to handle nav result returned from previous screen.
 * If multiple screen with same result handling logic stay in sequence only first encountered will
 * handle it, after that result will be removed.
 */
@Composable
fun <T> NavResultHandler(
    navResult: NavResult<T>,
    listener: (T) -> Unit
) {
    LaunchedEffect(Unit) {
        while (true) {
            val result = navResult.channel.receive()
            listener(result)
        }
    }
}