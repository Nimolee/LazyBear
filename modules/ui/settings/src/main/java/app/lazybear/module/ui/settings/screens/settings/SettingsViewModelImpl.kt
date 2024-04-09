package app.lazybear.module.ui.settings.screens.settings

import androidx.lifecycle.viewModelScope
import app.lazybear.module.data.preferences.PreferencesRepository
import com.lazybear.module.data.tmdb_api.TMDBRepository
import com.lazybear.module.data.tmdb_api.entities.Genre
import com.lazybear.module.data.tmdb_api.entities.ReleaseYear
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class SettingsViewModelImpl(
    private val _tmdbRepository: TMDBRepository,
    private val _preferencesRepository: PreferencesRepository,
) : SettingsViewModel() {
    override val yearsFlow: Flow<List<ReleaseYear>> = _tmdbRepository.yearsFlow
    override val genresFlow: Flow<List<Genre>> = _tmdbRepository.genresFlow
    override val selectedYearIndexFlow: Flow<Int?> = _preferencesRepository.selectedYearIndexFlow
    override val selectedGenresFlow: Flow<List<Genre>> =
        _tmdbRepository.genresFlow.combine(_preferencesRepository.selectedGenresIdsFlow) { genres, selected ->
            genres.filter { selected.contains(it.id) }
        }

    override fun selectYear(yearIndex: Int) {
        viewModelScope.launch {
            _preferencesRepository.selectYear(yearIndex)
        }
    }

    override fun selectGenre(genre: Genre) {
        viewModelScope.launch {
            _preferencesRepository.selectGenre(genre.id)
        }
    }

}