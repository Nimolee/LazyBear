package app.lazybear.module.ui.advice.screens.backdrops

import androidx.lifecycle.ViewModel
import com.lazybear.module.data.tmdb_api.entities.Movie
import kotlinx.coroutines.flow.Flow

abstract class BackdropViewModel : ViewModel() {
    abstract val loadingFlow: Flow<Boolean>
    abstract val movieFlow: Flow<Movie?>
    abstract val networkErrorFlow: Flow<Boolean>
    abstract val unknownErrorFlow: Flow<Boolean>

    abstract fun loadMovie(movieId: Int)

    abstract fun resetErrors()

    abstract fun tryAgain()
}