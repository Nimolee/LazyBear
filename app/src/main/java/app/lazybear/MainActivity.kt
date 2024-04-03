package app.lazybear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import app.lazybear.ui.theme.LazyBearTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    private val _viewModel: MainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        var keep: Boolean = true
        installSplashScreen().setKeepOnScreenCondition { keep }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            val isSuccess = _viewModel.preloadData()
            keep = false
        }
        setContent {
            KoinContext {
                LazyBearTheme {
                    MainNavigation()
                }
            }
        }

    }
}

