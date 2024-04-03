package app.lazybear.module.data.preferences

import kotlinx.coroutines.flow.StateFlow

interface PreferencesRepository {
    val tokenFlow: StateFlow<String?>
    val selectedYearIndexFlow: StateFlow<Int?>
    val selectedGenresIdsFlow: StateFlow<List<Int>>

    fun selectYear(yearIndex: Int?)

    fun selectGenre(genreId: Int)
}