package app.lazybear.module.ui.advice.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.lazybear.module.ui.localization.Localization
import com.lazybear.module.data.tmdb_api.entities.Keyword

@Composable
fun MovieKeywordsBlock(
    keywords: List<Keyword>,
    modifier: Modifier = Modifier,
) {
    if (keywords.isNotEmpty()) {
        Column(
            modifier = modifier.padding(
                horizontal = 16.dp,
                vertical = 12.dp,
            )
        ) {
            Text(
                text = stringResource(id = Localization.keywords_title),
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(),
            )
            Text(
                text = keywords.joinToString(", ") { it.name },
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}