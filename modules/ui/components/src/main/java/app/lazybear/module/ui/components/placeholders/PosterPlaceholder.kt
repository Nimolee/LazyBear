package app.lazybear.module.ui.components.placeholders

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.lazybear.module.ui.components.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun PosterPlaceholder(
    modifier:Modifier = Modifier,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_loader))

    LottieAnimation(
        composition = composition,
        progress = { 0f },
        modifier = modifier.size(220.dp)
    )
}