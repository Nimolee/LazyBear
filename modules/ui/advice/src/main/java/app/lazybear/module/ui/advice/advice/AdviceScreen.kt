package app.lazybear.module.ui.advice.advice

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.lazybear.localization.Localization
import app.lazybear.module.ui.advice.R
import app.lazybear.module.ui.advice.components.MovieBackdropBlock
import app.lazybear.module.ui.advice.components.MovieCastBlock
import app.lazybear.module.ui.advice.components.MovieCrewBlock
import app.lazybear.module.ui.advice.components.MovieDescriptionBlock
import app.lazybear.module.ui.advice.components.MovieKeywordsBlock
import app.lazybear.module.ui.advice.components.MoviePosterBlock
import app.lazybear.module.ui.advice.components.MovieSectionTitleBlock
import app.lazybear.module.ui.advice.components.MovieTitleBlock
import app.lazybear.module.ui.advice.components.MovieTrailerBlock
import app.lazybear.module.ui.advice.components.WatchProvidersBlock
import app.lazybear.module.ui.components.dialogs.ErrorDialog
import app.lazybear.module.ui.components.dialogs.NetworkErrorDialog
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.math.min

@Composable
fun AdviceScreen(
    arguments: AdviceArguments,
    onPreferencesOpen: () -> Unit,
    viewModel: AdviceViewModel = koinViewModel(),
) {
    val bottomInset = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = listState.isScrollingUp(),
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> 2 * fullHeight },
                    animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
                ),
                exit = slideOutVertically(
                    targetOffsetY = { fullHeight -> 2 * fullHeight },
                    animationSpec = tween(durationMillis = 250, easing = FastOutLinearInEasing)
                ),
            ) {
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
                    val loadingState = viewModel.loadingFlow.collectAsState(false)
                    OutlinedButton(
                        onClick = { onPreferencesOpen() },
                        contentPadding = PaddingValues(0.dp),
                        enabled = loadingState.value.not(),
                        modifier = Modifier.size(40.dp),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_profile_18),
                            contentDescription = null,
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    OutlinedButton(
                        onClick = {
                            viewModel.surprise()
                            scope.launch { listState.scrollToItem(0) }
                        },
                        enabled = loadingState.value.not(),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = stringResource(id = Localization.surprise_me_button))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = {
                            viewModel.shuffle()
                            scope.launch { listState.scrollToItem(0) }
                        },
                        enabled = loadingState.value.not(),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = stringResource(id = Localization.shuffle_button))
                    }
                }
            }
        }
    ) { insets ->
        val movieState = viewModel.movieFlow.collectAsState(null)
        movieState.value?.let { movie ->
            LazyColumn(
                state = listState,
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(
                    top = 16.dp + insets.calculateTopPadding(),
                    bottom = 16.dp + bottomInset,
                ),
                modifier = Modifier.fillMaxWidth(),
            ) {
                item {
                    MoviePosterBlock(posterUrl = movie.poster)
                }
                item {
                    MovieTitleBlock(
                        title = movie.title,
                        rating = movie.certification,
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
                item {
                    WatchProvidersBlock(
                        providers = movie.watchProviders,
                        movieTitle = movie.title,
                        releaseYear = movie.releaseDate.year,
                    )
                }
                item {
                    MovieCastBlock(cast = movie.cast)
                }
                item {
                    MovieTrailerBlock(trailers = movie.trailers)
                }
                if (movie.backdrops.isNotEmpty()) {
                    item {
                        MovieSectionTitleBlock(title = stringResource(id = Localization.backdrops_title))
                    }
                    items(min(movie.backdrops.size, 3)) { index: Int ->
                        MovieBackdropBlock(backdrop = movie.backdrops[index])
                    }
                    if (movie.backdrops.size > 3) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 12.dp,
                                        vertical = 16.dp,
                                    )
                            ) {
                                OutlinedButton(
                                    onClick = { /*TODO*/ },
                                ) {
                                    Text(text = stringResource(id = Localization.more_backdrops_button))
                                }
                            }
                        }
                    }
                }
                item { MovieKeywordsBlock(keywords = movie.keywords) }
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

        val networkErrorState = viewModel.networkErrorFlow.collectAsState(initial = false)
        if (networkErrorState.value) {
            NetworkErrorDialog(
                onDismiss = viewModel::resetErrors,
                onTryAgain = viewModel::tryAgain,
            )
        }
        val unknownErrorState = viewModel.unknownErrorFlow.collectAsState(initial = false)
        if (unknownErrorState.value) {
            ErrorDialog(
                message = stringResource(id = Localization.unknown_error_message),
                onDismiss = viewModel::resetErrors,
            )
        }
        val noResultsErrorState = viewModel.noResultsErrorFlow.collectAsState(initial = false)
        if (noResultsErrorState.value) {
            ErrorDialog(
                message = stringResource(id = Localization.no_results_error_message),
                onDismiss = viewModel::resetErrors,
            )
        }
    }
}

@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableIntStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}