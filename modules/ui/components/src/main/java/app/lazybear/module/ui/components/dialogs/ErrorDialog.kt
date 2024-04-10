package app.lazybear.module.ui.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import app.lazybear.module.ui.localization.Localization

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorDialog(
    message: String?,
    onDismiss: () -> Unit
) {
    AlertDialog(
        title = {
            Text(stringResource(id = Localization.error_title))
        },
        text = {
            message?.let { Text(it) }
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onDismiss,
            ) {
                Text(stringResource(id = Localization.ok_button))
            }
        },
    )
}