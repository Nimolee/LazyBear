package app.lazybear.module.ui.components.dialogs

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import app.lazybear.module.ui.components.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LoadingDialog() {
    Dialog(
        onDismissRequest = {}
    ){
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_loader))

        LottieAnimation(
            composition = composition,
            modifier = Modifier.size(220.dp)
        )
    }
}