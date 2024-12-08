package app.lazybear.module.ui.navigation

import kotlinx.coroutines.channels.Channel

/**
 * Helper to return result from one screen to another
 */
class NavResult<T> {
    internal val channel = Channel<T>(capacity = 1)

    fun trySend(value: T) {
        channel.trySend(value)
    }

    suspend fun send(value: T) {
        channel.send(value)
    }
}

