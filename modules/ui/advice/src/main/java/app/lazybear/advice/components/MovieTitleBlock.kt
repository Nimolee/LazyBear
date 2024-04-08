package app.lazybear.advice.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lazybear.module.data.tmdb_api.entities.Genre
import java.time.Duration

@Composable
fun MovieTitleBlock(
    title: String,
    rating: String,
    releaseYear: String,
    duration: Duration,
    genres: List<Genre>,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(
            vertical = 12.dp,
            horizontal = 16.dp,
        ),
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            composeInfoString(rating, releaseYear, duration),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = genres.joinToString(", ") { it.name },
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

private fun composeInfoString(
    rating: String,
    releaseYear: String,
    duration: Duration,
    separator: String = " · ",
): String {
    return if (rating.isNotEmpty()) {
        arrayOf(rating, releaseYear, formatDuration(duration))
    } else {
        arrayOf(releaseYear, formatDuration(duration))
    }.joinToString(separator)
}

private fun formatDuration(duration: Duration): String {
    val totalDuration = duration.toMinutes().toInt()
    val hours = totalDuration / (60)
    val minutes = totalDuration % 60
    var result = ""
    if (hours > 0) result += "${hours}h "
    if (minutes < 10) result += "0"
    result += "${minutes}m"
    return result
}