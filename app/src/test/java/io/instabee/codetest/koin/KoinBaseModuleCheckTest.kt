package io.instabee.codetest.koin

import io.instabee.codetest.BaseUnitTest
import io.instabee.codetest.repository.RouteRepo
import io.instabee.codetest.ui.route.RouteDetailsViewModel
import org.junit.Assert.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.parameter.parametersOf
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules

@RunWith(JUnit4::class)
@Category(CheckModuleTest::class)
class KoinBaseModuleCheckTest : BaseUnitTest() {
    @Test
    fun test_that_the_repos_are_created() {
        val routeRepo: RouteRepo = getKoin().get()
        assertNotNull(routeRepo)
    }

    @Test
    fun check_that_models_are_created() {
        getKoin().checkModules {
            create<RouteDetailsViewModel> { parametersOf("") }
        }
    }
}
