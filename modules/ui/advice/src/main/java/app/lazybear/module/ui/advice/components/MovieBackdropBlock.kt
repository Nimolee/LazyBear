package app.lazybear.module.ui.advice.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lazybear.module.data.tmdb_api.entities.MovieImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieBackdropBlock(
    backdrop: MovieImage,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(backdrop.aspectRatio)
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp,
            )
    ) {
        GlideImage(
            model = backdrop.link,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}