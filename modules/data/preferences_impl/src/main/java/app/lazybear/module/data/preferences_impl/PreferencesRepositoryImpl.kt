package app.lazybear.module.data.preferences_impl

import android.content.SharedPreferences
import androidx.core.content.edit
import app.lazybear.module.data.preferences.PreferencesRepository
import app.lazybear.module.data.preferences_impl.PreferencesConstants.SELECTED_GENRES_IDS
import app.lazybear.module.data.preferences_impl.PreferencesConstants.SELECTED_YEAR_INDEX
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PreferencesRepositoryImpl(
    private val _preferences: SharedPreferences,
    private val _securePreferences: SharedPreferences,
    private val _scope: CoroutineScope = CoroutineScope(IO),
    private val _gson: Gson = Gson(),
) : PreferencesRepository {
    companion object {
        const val TAG = "PreferencesRepositoryImpl"
        const val NO_YEAR_INDEX = -1
    }

    override val tokenFlow: MutableStateFlow<String?>
    override val selectedYearIndexFlow: MutableStateFlow<Int?>
    override val selectedGenresIdsFlow: MutableStateFlow<List<Int>>


    init {
        tokenFlow = MutableStateFlow(null)
        selectedYearIndexFlow = MutableStateFlow(null)
        selectedGenresIdsFlow = MutableStateFlow(emptyList())
        _scope.launch {
            val selectedYearIndex = _preferences.getInt(SELECTED_YEAR_INDEX, NO_YEAR_INDEX)
            if (selectedYearIndex != -1) {
                selectedYearIndexFlow.emit(selectedYearIndex)
            }
        }
        _scope.launch {
            val selectedGenresIds = _preferences.getString(SELECTED_GENRES_IDS, null)
            if (selectedGenresIds != null) {
                _gson.fromJson(selectedGenresIds, Array<Int>::class.java).also {
                    selectedGenresIdsFlow.emit(it.toList())
                }
            }
        }
    }

    override fun selectYear(yearIndex: Int?) {
        _scope.launch {
            if (selectedYearIndexFlow.value == yearIndex) {
                NO_YEAR_INDEX
            } else {
                yearIndex ?: NO_YEAR_INDEX
            }.let {
                _preferences.edit { putInt(SELECTED_YEAR_INDEX, it) }
                selectedYearIndexFlow.emit(it)
            }
        }
    }

    override fun selectGenre(genreId: Int) {
        _scope.launch {
            selectedGenresIdsFlow.value.let { genres ->
                val newGenres = if (!genres.contains(genreId)) {
                    genres + genreId
                } else {
                    genres.toMutableList() - genreId
                }
                _preferences.edit {
                    putString(
                        SELECTED_GENRES_IDS,
                        _gson.toJson(newGenres.toTypedArray()),
                    )
                }
                selectedGenresIdsFlow.emit(newGenres)
            }
        }
    }


}