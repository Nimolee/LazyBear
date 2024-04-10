package app.lazybear.module.ui.navigation

import kotlinx.coroutines.channels.Channel

class NavResult<T> {
    internal val channel = Channel<T>(capacity = 1)

    fun trySend(value: T) {
        channel.trySend(value)
    }

    suspend fun send(value: T) {
        channel.send(value)
    }
}

