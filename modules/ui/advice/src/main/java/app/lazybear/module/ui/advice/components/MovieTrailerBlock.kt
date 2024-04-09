package app.lazybear.module.ui.advice.components

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.lazybear.module.ui.advice.R
import app.lazybear.localization.Localization
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lazybear.module.data.tmdb_api.entities.MovieVideo

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieTrailerBlock(
    trailers: List<MovieVideo>,
    modifier: Modifier = Modifier,
) {
    if (trailers.isNotEmpty()) {
        Column(modifier = modifier.padding(top = 12.dp)) {
            Text(
                stringResource(id = Localization.trailers_title),
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )
            LazyRow(
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 12.dp,
                )
            ) {
                items(trailers.size) { index ->
                    val context = LocalContext.current
                    if (index != 0) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    Card(
                        onClick = {
                            context.startActivity(
                                Intent(
                                    ACTION_VIEW,
                                    Uri.parse(trailers[index].videoLink),
                                )
                            )
                        },
                        modifier = Modifier
                            .width(
                                if (trailers.size > 1) {
                                    ((LocalConfiguration.current.screenWidthDp - 16) / 1.1).dp
                                } else {
                                    (LocalConfiguration.current.screenWidthDp - 32).dp
                                },
                            )
                            .aspectRatio(16 / 9f)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            GlideImage(
                                model = trailers[index].thumbnailLink,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(
                                        color = Color.Black.copy(alpha = 0.8f),
                                        shape = CircleShape,
                                    )
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.play_arrow_round_24),
                                    tint = Color.White,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}