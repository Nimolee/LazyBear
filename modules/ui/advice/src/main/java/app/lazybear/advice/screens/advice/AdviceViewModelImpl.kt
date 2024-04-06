package app.lazybear.advice.screens.advice

import androidx.lifecycle.viewModelScope
import app.lazybear.module.data.preferences.PreferencesRepository
import app.lazybear.module.data.server.onSuccess
import com.lazybear.module.data.tmdb_api.TMDBRepository
import com.lazybear.module.data.tmdb_api.entities.Genre
import com.lazybear.module.data.tmdb_api.entities.Movie
import com.lazybear.module.data.tmdb_api.entities.ReleaseYear
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AdviceViewModelImpl(
    private val _tmdbRepository: TMDBRepository,
    private val _preferencesRepository: PreferencesRepository,
) : AdviceViewModel() {
    override val loadingFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val movieFlow: MutableStateFlow<Movie?> = MutableStateFlow(null)

    private var surpriseAttempt: Int = 0

    init {
        shuffle()
    }

    private suspend fun getSelectedGenres(): List<Genre> {
        val all = _tmdbRepository.genresFlow.first()
        val selectedIds = _preferencesRepository.selectedGenresIdsFlow.value
        return all.filter { selectedIds.contains(it.id) }
    }

    private suspend fun getSelectedYear(): ReleaseYear? {
        val selectedIndex = _preferencesRepository.selectedYearIndexFlow.value
        return selectedIndex?.let {
            _tmdbRepository.yearsFlow.first()[it]
        }
    }

    override fun surprise() {
        surpriseAttempt++
        viewModelScope.launch {
            loadingFlow.emit(true)
            movieFlow.emit(null)
            val years = _tmdbRepository.yearsFlow.first()
            _tmdbRepository.recommendMovie(
                emptyList(),
                releaseYear = ReleaseYear(
                    start = years[surpriseAttempt % years.size].start,
                    end = years.first().end,
                ),
            ).onSuccess {
                movieFlow.emit(it)
            }.also {
                loadingFlow.emit(false)
            }
        }
    }

    override fun shuffle() {
        viewModelScope.launch {
            loadingFlow.emit(true)
            movieFlow.emit(null)
            _tmdbRepository.recommendMovie(
                getSelectedGenres(),
                getSelectedYear(),
            ).onSuccess {
                movieFlow.emit(it)
            }.also {
                loadingFlow.emit(false)
            }
        }
    }
}