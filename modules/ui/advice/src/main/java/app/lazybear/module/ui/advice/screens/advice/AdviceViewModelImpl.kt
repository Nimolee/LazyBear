package app.lazybear.module.ui.advice.screens.advice

import androidx.lifecycle.viewModelScope
import app.lazybear.module.data.preferences.PreferencesRepository
import app.lazybear.module.data.server.Result
import com.lazybear.module.data.tmdb_api.TMDBRepository
import com.lazybear.module.data.tmdb_api.entities.Genre
import com.lazybear.module.data.tmdb_api.entities.Movie
import com.lazybear.module.data.tmdb_api.entities.ReleaseYear
import com.lazybear.module.data.tmdb_api.errors.GenresErrors
import com.lazybear.module.data.tmdb_api.errors.RecommendError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AdviceViewModelImpl(
    private val _tmdbRepository: TMDBRepository,
    private val _preferencesRepository: PreferencesRepository,
) : AdviceViewModel() {
    companion object {
        const val TAG = "AdviceViewModelImpl"

        private enum class RetryAction {
            SURPRISE,
            SHUFFLE,
        }
    }

    private val _errorsFlow: MutableStateFlow<Any?> = MutableStateFlow(null)

    override val loadingFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val movieFlow = _tmdbRepository.recommendedMovieFlow
    override val networkErrorFlow: Flow<Boolean> =
        _errorsFlow.map { it is GenresErrors.NetworkError || it is RecommendError.NetworkError }
    override val unknownErrorFlow: Flow<Boolean> =
        _errorsFlow.map { it is GenresErrors.UnknownError || it is RecommendError.UnknownError }
    override val noResultsErrorFlow: Flow<Boolean> =
        _errorsFlow.map { it is RecommendError.NoResults }

    private var _surpriseAttempt: Int = 0
    private var _retryAction: RetryAction? = null

    init {
        viewModelScope.launch {
            val movie = _tmdbRepository.recommendedMovieFlow.firstOrNull()
            if (movie == null) {
                shuffle()
            }
        }
    }

    private suspend fun getSelectedGenres(): List<Genre>? {
        return suspendCoroutine { continuation ->
            viewModelScope.launch {
                _tmdbRepository.loadGenres().onSuccess { all ->
                    val selectedIds = _preferencesRepository.selectedGenresIdsFlow.value
                    continuation.resume(all.filter { selectedIds.contains(it.id) })
                }.onError {
                    _errorsFlow.emit(it)
                    loadingFlow.emit(false)
                    continuation.resume(null)
                }
            }
        }
    }

    private suspend fun getSelectedYear(): ReleaseYear? {
        val selectedIndex = _preferencesRepository.selectedYearIndexFlow.value
        return selectedIndex?.let {
            _tmdbRepository.getYears()[it]
        }
    }

    private suspend fun recommendMovie(
        genres: List<Genre>,
        year: ReleaseYear?,
    ): Result<Movie, RecommendError> {
        return _tmdbRepository.recommendMovie(genres, year)
            .onError { _errorsFlow.emit(it) }
            .also { loadingFlow.emit(false) }
    }

    override fun surprise() {
        if (loadingFlow.value) return
        _retryAction = RetryAction.SURPRISE
        viewModelScope.launch {
            loadingFlow.emit(true)

            val years = _tmdbRepository.getYears()
            recommendMovie(
                genres = emptyList(),
                year = ReleaseYear(
                    start = years[_surpriseAttempt % years.size].start,
                    end = years.first().end,
                ),
            ).onSuccess {
                _surpriseAttempt++
            }
        }
    }

    override fun shuffle() {
        if (loadingFlow.value) return
        _retryAction = RetryAction.SHUFFLE
        viewModelScope.launch {
            loadingFlow.emit(true)

            val genres = getSelectedGenres() ?: return@launch
            val year = getSelectedYear()

            recommendMovie(genres, year)
        }
    }

    override fun resetErrors() {
        viewModelScope.launch {
            _errorsFlow.emit(null)
        }
    }

    override fun tryAgain() {
        viewModelScope.launch {
            resetErrors()
            when (_retryAction) {
                RetryAction.SURPRISE -> surprise()
                RetryAction.SHUFFLE -> shuffle()
                null -> Unit
            }
        }
    }
}