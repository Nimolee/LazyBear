package app.lazybear.module.ui.components.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import app.lazybear.module.ui.components.R
import app.lazybear.module.ui.components.styles.lightButtonStyleColors
import app.lazybear.module.ui.localization.Localization

@Composable
fun NetworkErrorDialog(
    onDismiss: () -> Unit,
    onTryAgain: () -> Unit,
) {
    Dialog(
        onDismissRequest = {}
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp),
        ) {
            Image(
                painter = painterResource(R.drawable.img_error_logo),
                contentDescription = null,
                modifier = Modifier.height(206.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                stringResource(id = Localization.network_error_title),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(16.dp))
            Text(
                stringResource(id = Localization.network_error_message),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(24.dp))
            Button(
                colors = lightButtonStyleColors(),
                onClick = onTryAgain,
            ) {
                Text(stringResource(id = Localization.try_again_button))
            }
        }
    }
}