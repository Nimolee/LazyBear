package app.lazybear.module.ui.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import app.lazybear.localization.Localization

@Composable
fun NetworkErrorDialog(
    onDismiss: () -> Unit,
    onTryAgain: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(id = Localization.network_error_title))
        },
        text = {
            Text(stringResource(id = Localization.network_error_message))
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
            ) {
                Text(stringResource(id = Localization.cancel_button))
            }
        },
        confirmButton = {
            TextButton(
                onClick = onTryAgain,
            ) {
                Text(stringResource(id = Localization.try_again_button))
            }
        }
    )

}