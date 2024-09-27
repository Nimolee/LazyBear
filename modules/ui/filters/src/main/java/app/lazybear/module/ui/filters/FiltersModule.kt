package app.lazybear.module.ui.filters

import app.lazybear.module.ui.filters.screens.filters.FiltersViewModel
import app.lazybear.module.ui.filters.screens.filters.FiltersViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun filtersModule() = module {
    viewModel<FiltersViewModel> { FiltersViewModelImpl(get(), get()) }
}