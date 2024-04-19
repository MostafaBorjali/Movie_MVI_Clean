package com.borjali.data.repository

import android.annotation.SuppressLint
import com.borjali.data.datasource.network.api.CleanAppApi
import com.borjali.data.datasource.network.utils.NetworkBoundResource
import com.borjali.domain.model.MovieDto
import com.borjali.domain.model.base.ServerResponse
import com.borjali.domain.repository.MovieRepository
import com.borjali.domain.utils.DataState
import com.borjali.domain.utils.DataState.SUCCESS
import com.borjali.domain.viewstate.MovieViewState
import com.borjali.domain.viewstate.StateOfView
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Implementation class of [MovieRepository]
 */
class MovieRepositoryImpl @Inject constructor(
    private val cleanAppApi: CleanAppApi
) : MovieRepository {

    /**
     * Fetches a list of movie data transfer objects (DTOs) from the server using a network bound resource pattern.
     *
     * This function uses `flow` to perform asynchronous network operations and emit data states.
     *
     * @param stateOfView The type of state associated with the data fetch (e.g., GetMovieList).
     * @param pageNumber The current page number for pagination (starts from 1).
     * @return A Flow<DataState<MovieViewState>> that emits data states based on the network operation:
     *   - SUCCESS: Emitted if the network call is successful with the retrieved list of movies.
     *   - Other possible states (depending on your implementation):
     *     - ERROR: Emitted if there's an error during the network call.
     *     - LOADING: Emitted while data is being fetched from the network.
     */
    override fun getListOfMovies(stateOfView: StateOfView, pageNumber: Int) = flow {
        emitAll(
            object : NetworkBoundResource<List<MovieDto>, Unit, MovieViewState>(
                apiCall = {
                    cleanAppApi.getListOfMovies(
                        page = pageNumber,
                        fromDate = getLastMonthDate(),
                        toDate = getCurrentDate()
                    )
                }
            ) {
                override suspend fun handleNetworkSuccess(
                    response: ServerResponse<List<MovieDto>?>?
                ): DataState<MovieViewState> {

                    return SUCCESS(
                        data = MovieViewState(workers = response?.results!!),
                        stateOfView = stateOfView,
                    )
                }
            }.result
        )
    }

    /**
     * Gets the current date formatted as a string in YYYY-MM-DD format.
     */
    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(Date())
    }

    /**
     * Gets the last month date formatted as a string in YYYY-MM-DD format.
     */
    @SuppressLint("SimpleDateFormat")
    private fun getLastMonthDate(): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.MONTH, -1) // Subtract 1 month
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(cal.time)
    }

}
