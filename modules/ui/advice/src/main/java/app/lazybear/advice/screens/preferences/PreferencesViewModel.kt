package app.lazybear.advice.screens.preferences

import androidx.lifecycle.ViewModel
import com.lazybear.module.data.tmdb_api.entities.Genre
import com.lazybear.module.data.tmdb_api.entities.ReleaseYear
import kotlinx.coroutines.flow.Flow

abstract class PreferencesViewModel() : ViewModel() {
    abstract val yearsFlow: Flow<List<ReleaseYear>>
    abstract val genresFlow: Flow<List<Genre>>
    abstract val selectedYearIndexFlow: Flow<Int?>
    abstract val selectedGenresFlow: Flow<List<Genre>>

    abstract fun selectYear(yearIndex: Int)

    abstract fun selectGenre(genre: Genre)
}