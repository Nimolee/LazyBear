package app.lazybear.module.ui.advice.components

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.lazybear.module.ui.advice.R
import app.lazybear.module.ui.localization.Localization
import com.lazybear.module.data.tmdb_api.entities.Provider
import com.lazybear.module.data.tmdb_api.entities.Provider.AppleTV
import com.lazybear.module.data.tmdb_api.entities.Provider.Crunchyroll
import com.lazybear.module.data.tmdb_api.entities.Provider.GooglePlayMovies
import com.lazybear.module.data.tmdb_api.entities.Provider.HBOMax
import com.lazybear.module.data.tmdb_api.entities.Provider.Netflix
import com.lazybear.module.data.tmdb_api.entities.Provider.RakutenTV
import com.lazybear.module.data.tmdb_api.entities.Provider.YouTube
import com.lazybear.module.data.tmdb_api.entities.WatchProvider

@Composable
fun WatchProvidersBlock(
    movieTitle: String,
    releaseYear: Int,
    providers: List<WatchProvider>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        val context = LocalContext.current
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant,
        )
        LazyRow(
            contentPadding = PaddingValues(
                vertical = 12.dp,
                horizontal = 16.dp,
            ),
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(providers.size) { index ->
                Image(
                    painter = painterResource(id = getIconForProvider(providers[index].provider)),
                    contentDescription = providers[index].name,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(64.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .clickable {
                            context.openLink(
                                getUriForProvider(
                                    providers[index].provider,
                                    movieTitle,
                                )
                            )
                        },
                )
            }
            item {
                Image(
                    painter = painterResource(id = R.drawable.logo_google_search),
                    contentDescription = stringResource(id = Localization.search_hint),
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .clickable {
                            context.openLink(getUriForGoogle(movieTitle, releaseYear))
                        },
                )
            }
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant,
        )
    }
}

private fun Context.openLink(uri: Uri) {
    startActivity(Intent(ACTION_VIEW, uri))
}

private fun getIconForProvider(provider: Provider): Int {
    return when (provider) {
        AppleTV -> R.drawable.logo_apple_tv
        GooglePlayMovies -> R.drawable.logo_google_play
        Netflix -> R.drawable.logo_netflix
        RakutenTV -> R.drawable.logo_rakuten_tv
        YouTube -> R.drawable.logo_youtube
        Crunchyroll -> R.drawable.logo_crunchyroll
        HBOMax -> R.drawable.logo_hbo_max
    }
}

private fun getUriForProvider(
    provider: Provider,
    movieName: String,
): Uri {
    return when (provider) {
        AppleTV -> "https://tv.apple.com/search?term=$movieName"
        GooglePlayMovies -> "https://play.google.com/store/search?q=$movieName&c=movies"
        Netflix -> "https://www.netflix.com/"
        RakutenTV -> "https://www.rakuten.tv/ua/search?q=$movieName"
        YouTube -> "https://www.youtube.com/results?search_query=$movieName"
        Crunchyroll -> "https://www.crunchyroll.com/search?q=$movieName"
        HBOMax -> "https://www.max.com"
    }.let { Uri.parse(it) }
}

private fun getUriForGoogle(
    movieName: String,
    releaseYear: Int,
): Uri {
    return "https://www.google.com/search?q=$movieName+$releaseYear".let { Uri.parse(it) }
}