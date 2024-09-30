package app.lazybear.module.ui.filters.screens.filters

import androidx.lifecycle.viewModelScope
import app.lazybear.module.data.preferences.PreferencesRepository
import com.lazybear.module.data.tmdb_api.TMDBRepository
import com.lazybear.module.data.tmdb_api.entities.Genre
import com.lazybear.module.data.tmdb_api.entities.ReleaseYear
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch

class FiltersViewModelImpl(
    private val _tmdbRepository: TMDBRepository,
    private val _preferencesRepository: PreferencesRepository,
) : FiltersViewModel() {
    override val yearsFlow: Flow<List<ReleaseYear>> = _tmdbRepository.yearsFlow
    override val genresFlow: Flow<List<Genre>> = _tmdbRepository.genresFlow
    override val selectedYearIndexFlow: MutableStateFlow<Int?> = MutableStateFlow(null)
    override val selectedGenresFlow: MutableStateFlow<List<Genre>> = MutableStateFlow(emptyList())

    init {
        clearFilters()
    }

    override fun selectYear(yearIndex: Int) {
        viewModelScope.launch {
            if (selectedYearIndexFlow.value == yearIndex) {
                selectedYearIndexFlow.emit(null)
            } else {
                selectedYearIndexFlow.emit(yearIndex)
            }
        }
    }

    override fun selectGenre(genre: Genre) {
        viewModelScope.launch {
            selectedGenresFlow.getAndUpdate { selected ->
                if (selected.contains(genre)) {
                    selected - genre
                } else {
                    selected + genre
                }
            }
        }
    }

    override fun clearFilters() {
        viewModelScope.launch {
            selectedYearIndexFlow.emit(_preferencesRepository.selectedYearIndexFlow.value)
        }
        viewModelScope.launch {
            val selected = _preferencesRepository.selectedGenresIdsFlow.value
            _tmdbRepository.genresFlow.collect { genres ->
                selectedGenresFlow.emit(genres.filter { selected.contains(it.id) })
            }
        }
    }

    override fun applyFilters() {
        viewModelScope.launch {
            _preferencesRepository.overrideYear(selectedYearIndexFlow.value)
            _preferencesRepository.overrideGenres(selectedGenresFlow.value.map { it.id })
        }
    }

}