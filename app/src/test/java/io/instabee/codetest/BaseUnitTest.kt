package io.instabee.codetest

import android.app.Application
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.preference.PreferenceManager
import io.instabee.codetest.koin.dataSourceModule
import io.instabee.codetest.koin.repositoryModule
import io.instabee.codetest.koin.viewModelModule
import io.mockk.mockkClass
import org.junit.Rule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.test.AutoCloseKoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule
import java.io.File

open class BaseUnitTest : AutoCloseKoinTest() {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockProvider = MockProviderRule.create { mockkClass(it, relaxed = true) }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        androidLogger()
        val app = mockkClass(Application::class, relaxed = true)
        androidContext(app)
        io.mockk.every { app.cacheDir } returns File.createTempFile("asd", "ff")

        val sharedPrefs = mockkClass(SharedPreferences::class, relaxed = true)
        io.mockk.every { PreferenceManager.getDefaultSharedPreferences(app) } returns sharedPrefs
        io.mockk.every { sharedPrefs.getString(any(), any()) } returns "mock"

        modules(dataSourceModule, viewModelModule, repositoryModule)
    }
}
