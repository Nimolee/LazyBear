package app.lazybear.module.ui.advice.screens.backdrops

import androidx.lifecycle.viewModelScope
import com.lazybear.module.data.tmdb_api.TMDBRepository
import com.lazybear.module.data.tmdb_api.entities.Movie
import com.lazybear.module.data.tmdb_api.errors.MovieError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class BackdropViewModelImpl(
    private val _tmdbRepository: TMDBRepository,
) : BackdropViewModel() {
    private val _errorsFlow: MutableStateFlow<Any?> = MutableStateFlow(null)

    override val loadingFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val movieFlow: MutableStateFlow<Movie?> = MutableStateFlow(null)
    override val networkErrorFlow: Flow<Boolean> = _errorsFlow.map { it is MovieError.NetworkError }
    override val unknownErrorFlow: Flow<Boolean> = _errorsFlow.map { it is MovieError.UnknownError }

    private var _movieId: Int? = null

    override fun loadMovie(movieId: Int) {
        _movieId = movieId
        viewModelScope.launch {
            loadingFlow.emit(true)
            _tmdbRepository.getMovie(movieId)
                .onSuccess { movieFlow.emit(it) }
                .onError { _errorsFlow.emit(it) }
                .also { loadingFlow.emit(false) }
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
            _movieId?.also { loadMovie(it) }
        }
    }
}