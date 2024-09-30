package app.lazybear.module.ui.advice.components

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.lazybear.module.ui.advice.R
import app.lazybear.module.ui.localization.Localization
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lazybear.module.data.tmdb_api.entities.MovieVideo
import kotlin.math.min

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieTrailerBlock(
    trailers: List<MovieVideo>,
    modifier: Modifier = Modifier,
) {
    if (trailers.isNotEmpty()) {
        Column(
            modifier = modifier
                .padding(top = 12.dp)
                .animateContentSize()
        ) {
            val context = LocalContext.current
            val expandedState = remember { mutableStateOf(false) }

            Text(
                stringResource(id = Localization.trailers_title),
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )
            trailers.subList(0, if (expandedState.value) trailers.size else min(trailers.size, 3))
                .forEach { trailer ->
                    Card(
                        onClick = {
                            context.startActivity(
                                Intent(
                                    ACTION_VIEW,
                                    Uri.parse(trailer.videoLink),
                                )
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 8.dp,
                            )
                            .aspectRatio(16 / 9f),
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            GlideImage(
                                model = trailer.thumbnailLink,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Image(
                                painter = painterResource(id = R.drawable.ic_play_youtube),
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.width(56.dp)
                            )
                        }
                    }
                }
            if (trailers.size > 3) {
                TextButton(
                    onClick = { expandedState.value = !expandedState.value },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 16.dp),
                ) {
                    Text(
                        stringResource(
                            id = if (expandedState.value) Localization.show_less_button else Localization.show_more_button
                        )
                    )
                }
            }
        }
    }
}