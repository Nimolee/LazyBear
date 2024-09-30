package app.lazybear.module.ui.components.cards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun GenreSelectionCard(
    title: String,
    iconPainter: Painter,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        OutlinedCard(
            onClick = onClick,
            colors = CardDefaults.outlinedCardColors().copy(
                containerColor = if (selected) MaterialTheme.colorScheme.secondary else Color.Transparent,
            ),
            modifier = Modifier
                .size(64.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Icon(
                    painter = iconPainter,
                    contentDescription = title,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Text(
            title,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}