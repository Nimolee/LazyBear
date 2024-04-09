package app.lazybear.module.ui.advice.advice

import androidx.lifecycle.viewModelScope
import app.lazybear.module.data.preferences.PreferencesRepository
import app.lazybear.module.data.server.Result
import com.lazybear.module.data.tmdb_api.TMDBRepository
import com.lazybear.module.data.tmdb_api.entities.Genre
import com.lazybear.module.data.tmdb_api.entities.Movie
import com.lazybear.module.data.tmdb_api.entities.ReleaseYear
import com.lazybear.module.data.tmdb_api.errors.GenresErrors
import com.lazybear.module.data.tmdb_api.errors.MovieError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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
    override val movieFlow: MutableStateFlow<Movie?> = MutableStateFlow(null)
    override val networkErrorFlow: Flow<Boolean> =
        _errorsFlow.map { it is GenresErrors.NetworkError || it is MovieError.NetworkError }
    override val unknownErrorFlow: Flow<Boolean> =
        _errorsFlow.map { it is GenresErrors.UnknownError || it is MovieError.UnknownError }
    override val noResultsErrorFlow: Flow<Boolean> = _errorsFlow.map { it is MovieError.NoResults }

    private var _surpriseAttempt: Int = 0
    private var _retryAction: RetryAction? = null

    init {
        shuffle()
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
    ): Result<Movie, MovieError> {
        return _tmdbRepository.recommendMovie(genres, year).onSuccess {
            movieFlow.emit(it)
            loadingFlow.emit(false)
        }.onError {
            _errorsFlow.emit(it)
            loadingFlow.emit(false)
        }
    }

    override fun surprise() {
        _retryAction = RetryAction.SURPRISE
        viewModelScope.launch {
            loadingFlow.emit(true)
            movieFlow.emit(null)
            _surpriseAttempt++

            val years = _tmdbRepository.getYears()
            recommendMovie(
                emptyList(),
                year = ReleaseYear(
                    start = years[_surpriseAttempt % years.size].start,
                    end = years.first().end,
                ),
            ).onError {
                _surpriseAttempt--
            }
        }
    }

    override fun shuffle() {
        _retryAction = RetryAction.SHUFFLE
        viewModelScope.launch {
            loadingFlow.emit(true)
            movieFlow.emit(null)

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