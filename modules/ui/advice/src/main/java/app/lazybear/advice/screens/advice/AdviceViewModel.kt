package app.lazybear.advice.screens.advice

import androidx.lifecycle.ViewModel
import com.lazybear.module.data.tmdb_api.entities.Movie
import kotlinx.coroutines.flow.Flow

abstract class AdviceViewModel : ViewModel() {
    abstract val loadingFlow: Flow<Boolean>
    abstract val movieFlow: Flow<Movie?>
    abstract val networkErrorFlow: Flow<Boolean>
    abstract val unknownErrorFlow: Flow<Boolean>
    abstract val noResultsErrorFlow: Flow<Boolean>

    abstract fun surprise()

    abstract fun shuffle()

    abstract fun resetErrors()

    abstract fun tryAgain()
}