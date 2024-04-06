package app.lazybear.advice.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.lazybear.localization.Localization
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lazybear.module.data.tmdb_api.entities.WatchProvider

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WatchProvidersBlock(
    providers: List<WatchProvider>,
    modifier: Modifier = Modifier,
) {
    if (providers.isNotEmpty()) {
        Column() {
            Text(
                text = stringResource(id = Localization.available_on_title),
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 12.dp),
            )
            LazyRow(
                contentPadding = PaddingValues(
                    vertical = 12.dp,
                    horizontal = 16.dp,
                ),
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(providers.size) { index ->
                    GlideImage(
                        model = providers[index].logoLink,
                        contentDescription = providers[index].name,
                        modifier = Modifier
                            .padding(start = if (index != 0) 16.dp else 0.dp)
                            .size(64.dp)
                            .clip(CircleShape)
                    )
                }
            }
        }

    }
}