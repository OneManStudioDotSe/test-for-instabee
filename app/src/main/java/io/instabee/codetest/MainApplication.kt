package io.instabee.codetest

import android.app.Application
import io.instabee.codetest.koin.dataSourceModule
import io.instabee.codetest.koin.repositoryModule
import io.instabee.codetest.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@Suppress("unused")
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MainApplication)
            modules(listOf(dataSourceModule, repositoryModule, viewModelModule))
        }
    }
}
