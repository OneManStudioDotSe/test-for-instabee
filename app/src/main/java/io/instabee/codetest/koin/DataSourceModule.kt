package io.instabee.codetest.koin

import androidx.preference.PreferenceManager
import io.instabee.codetest.api.MainApi
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataSourceModule = module {
    single { PreferenceManager.getDefaultSharedPreferences(androidApplication()) }
    single {
        Json {
            isLenient = true
            ignoreUnknownKeys = true
        }
    }
    single { MainApi() }
}
