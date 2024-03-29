package app.lazybear

import androidx.lifecycle.ViewModel

abstract class MainViewModel : ViewModel() {

    abstract suspend fun preloadData(): Boolean
}