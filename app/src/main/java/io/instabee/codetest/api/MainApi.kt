package io.instabee.codetest.api

import io.instabee.codetest.api.model.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Do not change anything in this file. Pretend this is the raw data you get from the API.
 */
class MainApi {
    fun getRoutes() = flow {
        delay(543)
        emit(mockedRoutes)
    }.flowOn(Dispatchers.IO)

    companion object {
        private val mockedRoutes = (1..50).map { Route.mock() }.distinctBy { it.id }.distinctBy { it.name }.take(30)
    }
}
