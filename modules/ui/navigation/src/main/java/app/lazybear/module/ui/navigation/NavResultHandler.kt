package app.lazybear.module.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

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