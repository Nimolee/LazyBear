package app.lazybear.advice.screens.advice

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.lazybear.advice.R
import app.lazybear.localization.Localization
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AdviceScreen(
    arguments: AdviceArguments,
    onPreferencesOpen: () -> Unit,
    viewModel: AdviceViewModel = koinViewModel(),
) {
    val bottomInset = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    Scaffold(
        bottomBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(
                    bottom = bottomInset + 12.dp,
                    top = 12.dp,
                    start = 16.dp,
                    end = 16.dp,
                )
            ) {
                OutlinedButton(
                    onClick = { onPreferencesOpen() },
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_profile_18),
                        contentDescription = null,
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(id = Localization.surprise_me_button))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Button(
                    onClick = {
                        viewModel.shuffle()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(id = Localization.shuffle_button))
                }
            }
        }
    ) { insets ->
        val movieState = viewModel.movieFlow.collectAsState(null)
        movieState.value?.let { movie ->
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(
                    top = 16.dp + insets.calculateTopPadding(),
                    bottom = 16.dp + insets.calculateBottomPadding(),
                ),
                modifier = Modifier.fillMaxWidth(),
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .width((LocalConfiguration.current.screenWidthDp * 0.8).dp)
                            .aspectRatio(6 / 9f)
                    ) {
                        GlideImage(
                            model = movie.poster,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

            }
        }
        val loadingState = viewModel.loadingFlow.collectAsState(false)
        if (loadingState.value) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                CircularProgressIndicator()
            }
        }
    }
}