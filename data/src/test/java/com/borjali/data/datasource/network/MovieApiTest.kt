package com.borjali.data.datasource.network

import com.borjali.data.DataConstants.GET_LIST_OF_MOVIES
import com.borjali.data.datasource.network.api.CleanAppApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@Suppress("OPT_IN_IS_NOT_ENABLED")
@RunWith(MockitoJUnitRunner::class)
class QuestionnaireApiTest : ApiAbstract<CleanAppApi>() {

    private lateinit var service: CleanAppApi

    @Before
    fun init() {
        service = createService(CleanAppApi::class.java)
    }


    @Throws(IOException::class)
    @Test
    fun `fetch success get questionnaires test`() = runTest {
        enqueueResponse(GET_LIST_OF_MOVIES)
        val response =
            service.getListOfMovies(page = 1, fromDate = "2024-02-02", toDate = "2024-03-02")
        mockWebServer.takeRequest()
        assertThat(response.page, `is`(1))

        if (response.results?.isNotEmpty()!!)
            assertThat(
                response.results?.firstOrNull()?.title,
                `is`("Dune: Part Two")
            )

    }


}

