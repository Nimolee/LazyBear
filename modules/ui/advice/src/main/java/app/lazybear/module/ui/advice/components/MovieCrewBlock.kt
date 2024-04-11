package app.lazybear.module.ui.advice.components

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
import androidx.compose.runtime.remember
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
            val sortedCrew: List<CrewMember> = remember {
                val sorted = mutableListOf<CrewMember>()
                for (index in crew.indices) {
                    val member = crew[index]
                    if (sorted.none { it.name == member.name }) {
                        sorted.add(
                            CrewMember(
                                member.id,
                                name = member.name,
                                profile = member.profile,
                                department = member.department,
                                job = crew.filter { it.name == member.name }
                                    .joinToString(", ") { it.job }
                            )
                        )
                    }
                }
                sorted
            }
            for (index in 0..sortedCrew.count() step 2) {
                if (index != 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Row {
                    CrewItem(
                        member = sortedCrew.getOrNull(index),
                        modifier = Modifier.weight(1f),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    CrewItem(
                        member = sortedCrew.getOrNull(index + 1),
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

