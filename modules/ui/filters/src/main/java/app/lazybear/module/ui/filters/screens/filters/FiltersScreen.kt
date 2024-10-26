package app.lazybear.module.ui.filters.screens.filters

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.lazybear.module.ui.components.buttons.HideButton
import app.lazybear.module.ui.components.cards.GenreSelectionCard
import app.lazybear.module.ui.components.styles.darkButtonStyleColors
import app.lazybear.module.ui.components.styles.lightButtonStyleColors
import app.lazybear.module.ui.filters.components.SelectionTitleBlock
import app.lazybear.module.ui.filters.helpers.iconRes
import app.lazybear.module.ui.filters.helpers.nameRes
import app.lazybear.module.ui.localization.Localization
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FiltersScreen(
    arguments: FiltersArguments,
    navigator: FiltersNavigator,
    viewModel: FiltersViewModel = koinViewModel(),
) {
    val bottomInset = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    BackHandler {
        navigator.close(false)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    HideButton {
                        navigator.close(false)
                    }
                },
                title = {
                    Text(stringResource(Localization.filters_title))
                },
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(bottom = bottomInset)
            ) {
                Button(
                    onClick = {
                        viewModel.clearFilters()
                    },
                    colors = darkButtonStyleColors(),
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 12.dp)
                        .padding(start = 16.dp, end = 8.dp),
                ) {
                    Text(stringResource(id = Localization.clear_button))
                }
                Button(
                    onClick = {
                        viewModel.applyFilters()
                        navigator.close(true)
                    },
                    colors = lightButtonStyleColors(),
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 12.dp)
                        .padding(start = 8.dp, end = 16.dp),
                ) {
                    Text(stringResource(id = Localization.apply_button))
                }
            }
        },
    ) { insets ->
        val genresState = viewModel.genresFlow.collectAsState(emptyList())
        val selectedGenresState = viewModel.selectedGenresFlow.collectAsState(emptyList())

        LazyColumn(
            contentPadding = PaddingValues(
                top = insets.calculateTopPadding(),
                bottom = insets.calculateBottomPadding() + 16.dp,
            )
        ) {
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
                            label = {
                                Text(
                                    when (index) {
                                        0 -> stringResource(id = Localization.last_two_years)
                                        yearsState.value.size - 1 -> stringResource(id = Localization.until_80s)
                                        else -> "${year.start.year}-${year.end.year}"
                                    }
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors().copy(
                                selectedContainerColor = MaterialTheme.colorScheme.secondary,
                            ),
                            onClick = {
                                viewModel.selectYear(index)
                            },
                            modifier = Modifier.padding(horizontal = 4.dp),
                        )
                    }
                }
            }
            item {
                SelectionTitleBlock(
                    title = stringResource(id = Localization.genre_selection_title),
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 12.dp,
                    ),
                )
            }
            genresState.value.chunked(4).forEach { chunk ->
                item {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                            .padding(bottom = 16.dp)
                    ) {
                        chunk.forEach { genre ->
                            val selected = selectedGenresState.value.any { it.id == genre.id }

                            GenreSelectionCard(
                                title = stringResource(genre.nameRes),
                                iconPainter = painterResource(genre.iconRes),
                                selected = selected,
                                onClick = {
                                    viewModel.selectGenre(genre)
                                },
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .width(64.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}