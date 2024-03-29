package app.lazybear

import androidx.lifecycle.viewModelScope
import com.lazybear.module.data.tmdb_api.TMDBRepository
import kotlinx.coroutines.async

class MainViewModelImpl(
    private val _tmdbRepository: TMDBRepository,
) : MainViewModel() {


    override suspend fun preloadData(): Boolean {
        return viewModelScope.async {
            return@async _tmdbRepository.loadGenres().isSuccess
        }.await()
    }
}