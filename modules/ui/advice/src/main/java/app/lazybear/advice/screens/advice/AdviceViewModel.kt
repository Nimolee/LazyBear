package app.lazybear.advice.screens.advice

import androidx.lifecycle.ViewModel
import com.lazybear.module.data.tmdb_api.entities.Movie
import kotlinx.coroutines.flow.Flow

abstract class AdviceViewModel : ViewModel() {
    abstract val loadingFlow: Flow<Boolean>
    abstract val movieFlow: Flow<Movie?>

    abstract fun surprise()

    abstract fun shuffle()
}