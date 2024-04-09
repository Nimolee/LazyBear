package app.lazybear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import app.lazybear.module.utils.log.logD
import app.lazybear.ui.theme.LazyBearTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    private val _viewModel: MainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val showSplashScreenState: MutableState<Boolean> = mutableStateOf(true)
        installSplashScreen().setKeepOnScreenCondition { showSplashScreenState.value }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            val isSuccess = _viewModel.preloadData()
            logD(TAG, "onCreate") { "data preload isSuccess=$isSuccess" }
            showSplashScreenState.value = false
        }
        setContent {
            if (!showSplashScreenState.value) {
                KoinContext {
                    LazyBearTheme {
                        MainNavigation()
                    }
                }
            }
        }

    }
}

