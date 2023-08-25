package io.instabee.codetest.koin

import io.instabee.codetest.ui.route.RouteDetailsViewModel
import io.instabee.codetest.ui.routelist.RouteListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { RouteListViewModel(get(), get()) }
    viewModel { (s: String) -> RouteDetailsViewModel(s, get()) }
}
