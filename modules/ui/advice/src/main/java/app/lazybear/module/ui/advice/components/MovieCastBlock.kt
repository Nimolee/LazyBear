package app.lazybear.module.ui.advice.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.lazybear.module.ui.localization.Localization
import com.lazybear.module.data.tmdb_api.entities.CastMember

@Composable
fun MovieCastBlock(
    cast: List<CastMember>,
    modifier: Modifier = Modifier,
) {
    if (cast.isNotEmpty()) {
        Column(modifier = modifier) {
            Text(
                text = stringResource(id = Localization.cast_title),
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
                    horizontal = 8.dp,
                ),
            ) {
                items(cast.size) { index ->
                    CastMemberCard(
                        member = cast[index],
                        modifier = Modifier.width((LocalConfiguration.current.screenWidthDp / 2.5 - 8).dp)
                    )
                }
            }
        }
    }
}