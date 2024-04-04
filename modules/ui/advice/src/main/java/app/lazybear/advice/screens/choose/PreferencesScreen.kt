package app.lazybear.advice.screens.choose

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.lazybear.advice.R
import app.lazybear.advice.components.SelectionTitleBlock
import app.lazybear.localization.Localization
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChooseScreen(
    arguments: PreferencesArguments,
    viewModel: PreferencesViewModel = koinViewModel(),
) {
    val bottomInset = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = bottomInset)
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 12.dp,
                        )
                ) {
                    Text(stringResource(id = Localization.shuffle_button))
                }
            }
        }
    ) { insets ->
        LazyColumn(
            contentPadding = PaddingValues(
                top = insets.calculateTopPadding() + 16.dp,
                bottom = insets.calculateBottomPadding() + 16.dp,
            )
        ) {
            item {
                SelectionTitleBlock(
                    title = stringResource(id = Localization.genre_selection_title),
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 12.dp,
                    ),
                )
            }
            item {
                val genresState = viewModel.genresFlow.collectAsState(emptyList())
                val selectedGenresState = viewModel.selectedGenresFlow.collectAsState(emptyList())

                FlowRow(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            top = 8.dp,
                            end = 16.dp,
                            bottom = 20.dp,
                        )
                ) {
                    genresState.value.forEach { genre ->
                        val selected = selectedGenresState.value.any { it.id == genre.id }
                        FilterChip(
                            selected = selected,
                            leadingIcon = if (selected) {
                                { Icon(painterResource(id = R.drawable.ic_selected_18), null) }
                            } else null,
                            label = {
                                Text(genre.name)
                            },
                            onClick = {
                                viewModel.selectGenre(genre)
                            },
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .animateContentSize(),
                        )
                    }
                }
            }
            item {
                SelectionTitleBlock(
                    title = stringResource(id = Localization.year_title),
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 12.dp,
                    ),
                )
            }
            item {
                val yearsState = viewModel.yearsFlow.collectAsState(emptyList())
                val selectedYearIndexState = viewModel.selectedYearIndexFlow.collectAsState(null)

                FlowRow(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            top = 8.dp,
                            end = 16.dp,
                            bottom = 20.dp,
                        )
                ) {
                    yearsState.value.forEachIndexed { index, year ->
                        val selected = index == selectedYearIndexState.value
                        FilterChip(
                            selected = selected,
                            leadingIcon = if (selected) {
                                { Icon(painterResource(id = R.drawable.ic_selected_18), null) }
                            } else null,
                            label = {
                                Text(
                                    if (index == 0) {
                                        stringResource(id = Localization.last_two_years)
                                    } else {
                                        "${year.start.year}-${year.end.year + 1}"
                                    }
                                )
                            },
                            onClick = {
                                viewModel.selectYear(index)
                            },
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .animateContentSize(),
                        )
                    }
                }
            }
        }
    }
}