package app.lazybear.module.ui.advice.components

import android.icu.number.CompactNotation
import android.icu.number.NumberFormatter
import android.icu.text.DecimalFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.os.ConfigurationCompat
import androidx.core.os.LocaleListCompat
import app.lazybear.module.ui.advice.R
import com.lazybear.module.data.tmdb_api.entities.Genre
import java.time.Duration
import java.util.Locale

@Composable
fun MovieTitleBlock(
    title: String,
    rating: String,
    releaseYear: String,
    duration: Duration,
    genres: List<Genre>,
    voteAverage: Float,
    voteCount: Int,
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
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            val configuration = LocalConfiguration.current
            val locale = ConfigurationCompat.getLocales(configuration).get(0)
                ?: LocaleListCompat.getDefault()[0]!!
            Image(
                painter = painterResource(id = R.drawable.ic_tmdb),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .height(18.dp)
                    .background(
                        Color(3, 37, 65),
                        shape = RoundedCornerShape(corner = CornerSize(2.dp))
                    )
                    .padding(
                        horizontal = 4.dp,
                        vertical = 6.dp,
                    )
            )
            val voteString = buildAnnotatedString {
                withStyle(MaterialTheme.typography.titleLarge.toSpanStyle()) {
                    DecimalFormat("#.#").format(voteAverage).also {
                        append(it)
                    }
                }
                withStyle(MaterialTheme.typography.bodyMedium.toSpanStyle()) {
                    append("/10 ")
                    NumberFormatter.withLocale(Locale.US)
                        .notation(CompactNotation.compactShort())
                        .format(voteCount).also {
                            append("($it)")
                        }
                }
            }
            Text(
                text = voteString,
                style = MaterialTheme.typography.titleMedium,
            )
        }
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