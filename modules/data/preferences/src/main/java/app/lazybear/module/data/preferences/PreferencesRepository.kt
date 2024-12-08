package app.lazybear.module.data.preferences

import kotlinx.coroutines.flow.StateFlow

interface PreferencesRepository {
    /**
     * Flow contains authorization token
     * May be null if user is not authorized
     */
    val tokenFlow: StateFlow<String?>

    /**
     * Flow that store selected year index used for filter functionality.
     */
    val selectedYearIndexFlow: StateFlow<Int?>

    /**
     * Flow that store list of selected genres for filter functionality.
     */
    val selectedGenresIdsFlow: StateFlow<List<Int>>

    /**
     * Store information about selected year index in local storage.
     * This method represent toggle selection so value will be reset in case if it same as previous.
     */
    fun selectYear(yearIndex: Int?)

    /**
     * Store information about selected genres in local storage.
     * This method represent single value toggle so id will be removed from list if already exist.
     */
    fun selectGenre(genreId: Int)

    /**
     * Store information about selected year index in local storage.
     */
    fun overrideYear(yearIndex: Int?)

    /**
     * Store information about selected genres in local storage.
     */
    fun overrideGenres(newGenres: List<Int>)
}