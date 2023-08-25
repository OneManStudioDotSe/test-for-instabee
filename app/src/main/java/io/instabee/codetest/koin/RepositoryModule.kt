package io.instabee.codetest.koin

import io.instabee.codetest.repository.RouteRepo
import org.koin.dsl.module

val repositoryModule = module {
    single { RouteRepo(get(), get()) }
}
