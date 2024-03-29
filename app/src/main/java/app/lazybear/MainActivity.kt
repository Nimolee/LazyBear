package app.lazybear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import app.lazybear.ui.theme.LazyBearTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    private val _viewModel: MainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinContext {
                LazyBearTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Column {
                            Text(
                                text = "Hello!",
                                modifier = Modifier.padding(innerPadding)
                            )
                            Button(
                                onClick = {
                                    lifecycleScope.launch {
                                        _viewModel.preloadData()
                                    }
                                },
                            ) {
                                Text("Title")
                            }
                        }
                    }
                }
            }
        }
    }
}

