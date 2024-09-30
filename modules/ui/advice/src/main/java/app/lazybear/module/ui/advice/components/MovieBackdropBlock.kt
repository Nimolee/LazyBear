package app.lazybear.module.ui.advice.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.lazybear.module.ui.components.cards.BackdropCard
import app.lazybear.module.ui.localization.Localization
import com.lazybear.module.data.tmdb_api.entities.MovieImage
import kotlin.math.min

@Composable
fun MovieBackdropBlock(
    backdrops: List<MovieImage>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(top = 12.dp)
            .animateContentSize()
    ) {
        val expandedState = remember { mutableStateOf(false) }

        Text(
            stringResource(id = Localization.backdrops_title),
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 6.dp)
        )
        backdrops.subList(0, if (expandedState.value) backdrops.size else min(backdrops.size, 3))
            .forEach { backdrop ->
                BackdropCard(
                    imageUrl = backdrop.link,
                    aspectRatio = backdrop.aspectRatio,
                    modifier = Modifier
                        .padding(top = 2.dp),
                )
            }
        if (backdrops.size > 3) {
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