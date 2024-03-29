package app.lazybear.module.data.preferences_impl

import android.content.SharedPreferences
import app.lazybear.module.data.preferences.PreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PreferencesRepositoryImpl(
    private val _preferences: SharedPreferences,
    private val _securePreferences: SharedPreferences,
) : PreferencesRepository {
    override val tokenFlow: MutableStateFlow<String?>

    init {
        tokenFlow = MutableStateFlow(null)
    }

}