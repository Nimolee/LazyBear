package app.lazybear.module.ui.advice.screens.backdrops

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.lazybear.module.ui.components.buttons.HideButton
import app.lazybear.module.ui.components.cards.BackdropCard
import app.lazybear.module.ui.components.dialogs.ErrorDialog
import app.lazybear.module.ui.components.dialogs.NetworkErrorDialog
import app.lazybear.module.ui.localization.Localization
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackdropScreen(
    arguments: BackdropArguments,
    navigator: BackdropNavigator,
    viewModel: BackdropViewModel = koinViewModel(),
) {
    LaunchedEffect(arguments.movieId) {
        viewModel.loadMovie(arguments.movieId)
    }

    BackHandler {
        navigator.close()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    HideButton {
                        navigator.close()
                    }
                },
                title = { },
            )
        },
    ) { insets ->
        val movieState = viewModel.movieFlow.collectAsState(null)
        movieState.value?.let { movie ->
            LazyColumn(
                contentPadding = PaddingValues(
                    top = insets.calculateTopPadding() + 16.dp,
                    bottom = insets.calculateBottomPadding() + 16.dp,
                )
            ) {
                items(movie.backdrops.size) { index ->
                    BackdropCard(
                        imageUrl =
                        movie.backdrops[index].link,
                        aspectRatio = movie.backdrops[index].aspectRatio,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = if (index != 0) 8.dp else 0.dp),
                    )
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
    }
}