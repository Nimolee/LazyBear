package app.lazybear.advice.screens.advice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.lazybear.advice.R
import app.lazybear.advice.components.MovieCrewBlock
import app.lazybear.advice.components.MovieDescriptionBlock
import app.lazybear.advice.components.MoviePosterBlock
import app.lazybear.advice.components.MovieTitleBlock
import app.lazybear.localization.Localization
import org.koin.androidx.compose.koinViewModel

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
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(
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
                    MoviePosterBlock(posterUrl = movie.poster)
                }
                item {
                    MovieTitleBlock(
                        title = movie.title,
                        rating = "PG",
                        releaseYear = movie.releaseDate.year.toString(),
                        duration = movie.duration,
                        genres = movie.genres,
                    )
                }
                item {
                    MovieDescriptionBlock(description = movie.overview)
                }
                item {
                    MovieCrewBlock(crew = movie.crew)
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