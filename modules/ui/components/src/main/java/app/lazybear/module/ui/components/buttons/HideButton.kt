package app.lazybear.module.ui.components.buttons

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import app.lazybear.module.ui.components.R
import app.lazybear.module.ui.localization.Localization

@Composable
fun HideButton(
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_down_24),
            contentDescription = stringResource(id = Localization.close_button),
        )
    }
}