package app.lazybear.advice.screens.advice

import androidx.lifecycle.viewModelScope
import app.lazybear.module.data.preferences.PreferencesRepository
import app.lazybear.module.data.server.onSuccess
import app.lazybear.module.utils.log.logD
import com.lazybear.module.data.tmdb_api.TMDBRepository
import com.lazybear.module.data.tmdb_api.entities.Genre
import com.lazybear.module.data.tmdb_api.entities.Movie
import com.lazybear.module.data.tmdb_api.entities.ReleaseYear
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AdviceViewModelImpl(
    private val _tmdbRepository: TMDBRepository,
    private val _preferencesRepository: PreferencesRepository,
) : AdviceViewModel() {
    companion object {
        const val TAG = "AdviceViewModelImpl"
    }

    override val loadingFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val movieFlow: MutableStateFlow<Movie?> = MutableStateFlow(null)

    private var surpriseAttempt: Int = 0

    init {
        shuffle()
    }

    private suspend fun getSelectedGenres(): List<Genre> {
        val all = _tmdbRepository.genresFlow.value
        val selectedIds = _preferencesRepository.selectedGenresIdsFlow.value
        return all.filter { selectedIds.contains(it.id) }
    }

    private suspend fun getSelectedYear(): ReleaseYear? {
        val selectedIndex = _preferencesRepository.selectedYearIndexFlow.value
        return selectedIndex?.let {
            _tmdbRepository.yearsFlow.value[it]
        }
    }

    override fun surprise() {
        surpriseAttempt++
        viewModelScope.launch {
            loadingFlow.emit(true)
            movieFlow.emit(null)
            val years = _tmdbRepository.yearsFlow.value
            _tmdbRepository.recommendMovie(
                emptyList(),
                releaseYear = ReleaseYear(
                    start = years[surpriseAttempt % years.size].start,
                    end = years.first().end,
                ),
            ).onSuccess {
                if (it?.bad == true) {
                    logD(TAG, "surprise") { "Bad movie found, let's try again" }
                    surpriseAttempt--
                    surprise()
                } else {
                    movieFlow.emit(it)
                }
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
                if (it?.bad == true) {
                    logD(TAG, "shuffle") { "Bad movie found, let's try again" }
                    shuffle()
                } else {
                    movieFlow.emit(it)
                }
            }.also {
                loadingFlow.emit(false)
            }
        }
    }
}