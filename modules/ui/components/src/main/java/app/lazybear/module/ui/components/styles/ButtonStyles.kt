package app.lazybear.module.ui.components.styles

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun lightButtonStyleColors() = ButtonColors(
    containerColor = MaterialTheme.colorScheme.primary,
    contentColor = MaterialTheme.colorScheme.secondary,
    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
    disabledContentColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
)

@Composable
fun darkButtonStyleColors() = ButtonColors(
    containerColor = MaterialTheme.colorScheme.secondary,
    contentColor = MaterialTheme.colorScheme.primary,
    disabledContainerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
    disabledContentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
)