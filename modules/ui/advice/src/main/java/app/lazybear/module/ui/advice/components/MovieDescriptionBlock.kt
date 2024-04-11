package app.lazybear.module.ui.advice.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.lazybear.module.ui.localization.Localization
import com.lazybear.module.data.tmdb_api.entities.CrewMember

private const val DISPLAY_COUNT = 3

@Composable
fun MovieDescriptionBlock(
    description: String,
    crew: List<CrewMember>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(
                vertical = 12.dp,
                horizontal = 16.dp,
            )
            .animateContentSize()
    ) {
        val expandedState = remember { mutableStateOf(false) }
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start,
            maxLines = if (!expandedState.value) DISPLAY_COUNT else Int.MAX_VALUE,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
        )
        if (expandedState.value) {
            val sortedCrew: List<CrewMember> = remember {
                val sorted = mutableListOf<CrewMember>()
                for (index in crew.indices) {
                    val member = crew[index]
                    if (sorted.none { it.job == member.job }) {
                        sorted.add(
                            CrewMember(
                                0,
                                name = crew.filter { it.job == member.job }
                                    .joinToString(", ") { it.name },
                                profile = null,
                                department = member.department,
                                job = member.job,
                            )
                        )
                    }
                }
                sorted
            }
            for (index in sortedCrew.indices) {
                if (index + 1 <= DISPLAY_COUNT || expandedState.value) {
                    CrewItem(
                        member = sortedCrew.getOrNull(index),
                        modifier = Modifier.padding(top = if (index != 0) 8.dp else 0.dp),
                    )
                }
            }
        }
        if (!expandedState.value) {
            TextButton(
                onClick = { expandedState.value = !expandedState.value },
                modifier = Modifier.align(Alignment.End),
            ) {
                Text(stringResource(id = Localization.show_more_button))
            }
        } else {
            TextButton(
                onClick = { expandedState.value = !expandedState.value },
                modifier = Modifier.align(Alignment.End),
            ) {
                Text(stringResource(id = Localization.show_less_button))
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