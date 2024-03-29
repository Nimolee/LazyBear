package app.lazybear.module.data.preferences

import kotlinx.coroutines.flow.StateFlow

interface PreferencesRepository {
    val tokenFlow: StateFlow<String?>
}