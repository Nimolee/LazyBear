package app.lazybear.advice.screens.advice

import androidx.lifecycle.viewModelScope
import app.lazybear.module.data.preferences.PreferencesRepository
import app.lazybear.module.data.server.onSuccess
import com.lazybear.module.data.tmdb_api.TMDBRepository
import com.lazybear.module.data.tmdb_api.entities.Genre
import com.lazybear.module.data.tmdb_api.entities.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AdviceViewModelImpl(
    private val _tmdbRepository: TMDBRepository,
    private val _preferencesRepository: PreferencesRepository,
) : AdviceViewModel() {
    override val loadingFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val movieFlow: MutableStateFlow<Movie?> = MutableStateFlow(null)

    private suspend fun getSelectedGenres(): List<Genre> {
        val all = _tmdbRepository.genresFlow.first()
        val selectedIds = _preferencesRepository.selectedGenresIdsFlow.value
        return all.filter { selectedIds.contains(it.id) }
    }

    override fun surprise() {
        TODO("Not yet implemented")
    }

    override fun shuffle() {
        viewModelScope.launch {
            loadingFlow.emit(true)
            _tmdbRepository.recommendMovie(getSelectedGenres()).onSuccess {
                movieFlow.emit(it)
            }.also {
                loadingFlow.emit(false)
            }
        }
    }
}