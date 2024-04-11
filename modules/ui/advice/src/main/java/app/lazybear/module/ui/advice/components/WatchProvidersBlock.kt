package app.lazybear.module.ui.advice.components

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.lazybear.module.ui.advice.R
import app.lazybear.module.ui.localization.Localization
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.lazybear.module.data.tmdb_api.entities.Provider
import com.lazybear.module.data.tmdb_api.entities.Provider.AppleTV
import com.lazybear.module.data.tmdb_api.entities.Provider.Crunchyroll
import com.lazybear.module.data.tmdb_api.entities.Provider.GooglePlayMovies
import com.lazybear.module.data.tmdb_api.entities.Provider.HBOMax
import com.lazybear.module.data.tmdb_api.entities.Provider.Netflix
import com.lazybear.module.data.tmdb_api.entities.Provider.RakutenTV
import com.lazybear.module.data.tmdb_api.entities.Provider.YouTube
import com.lazybear.module.data.tmdb_api.entities.WatchProvider

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WatchProvidersBlock(
    movieTitle: String,
    releaseYear: Int,
    providers: List<WatchProvider>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        val context = LocalContext.current
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
                Image(
                    painter = painterResource(id = getIconForProvider(providers[index].provider)),
                    contentDescription = providers[index].name,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(64.dp)
                        .clip(CircleShape)
                        .clickable {
                            context.startActivity(
                                Intent(
                                    ACTION_VIEW, getUriForProvider(
                                        providers[index].provider,
                                        movieTitle,
                                    )
                                )
                            )
                        },
                )
            }
            item {
                Box(modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .clickable {
                        context.startActivity(
                            Intent(
                                ACTION_VIEW,
                                Uri.parse("https://www.google.com/search?q=$movieTitle+$releaseYear")
                            )
                        )
                    }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = stringResource(id = Localization.search_hint),
                        modifier = Modifier
                            .background(Color.White)
                            .padding(12.dp)
                    )
                }
            }
        }
    }
}

private fun getIconForProvider(provider: Provider): Int {
    return when (provider) {
        AppleTV -> R.drawable.ic_apple_tv
        GooglePlayMovies -> R.drawable.ic_google_play
        Netflix -> R.drawable.ic_netflix
        RakutenTV -> R.drawable.ic_rakuten_tv
        YouTube -> R.drawable.ic_youtube
        Crunchyroll -> R.drawable.ic_crunchyroll
        HBOMax -> R.drawable.ic_hbo_max
    }
}

private fun getUriForProvider(provider: Provider, movieName: String): Uri {
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