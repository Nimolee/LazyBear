package app.lazybear.advice.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lazybear.module.data.tmdb_api.entities.CrewMember

@Composable
fun MovieCrewBlock(
    crew: List<CrewMember>,
    modifier: Modifier = Modifier,
) {
    if (crew.isNotEmpty()) {
        Column(
            modifier = modifier.padding(
                vertical = 12.dp,
                horizontal = 16.dp,
            )
        ) {
            for (index in 0..crew.count() step 2) {
                if (index != 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Row {
                    CrewItem(
                        member = crew.getOrNull(index),
                        modifier = Modifier.weight(1f),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    CrewItem(
                        member = crew.getOrNull(index + 1),
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
private fun CrewItem(
    member: CrewMember?,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        if (member != null) {
            Text(
                text = member.job,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(),
            )
            Text(
                text = member.name,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

