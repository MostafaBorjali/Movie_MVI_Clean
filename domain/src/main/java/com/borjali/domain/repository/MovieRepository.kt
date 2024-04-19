package com.borjali.domain.repository

import com.borjali.domain.utils.DataState
import com.borjali.domain.viewstate.StateOfView
import com.borjali.domain.viewstate.MovieViewState
import kotlinx.coroutines.flow.Flow

/**
 * Methods of WorkerRepository
 */
interface MovieRepository {
    /**
     * this function will get all worker of order subset .
     * @param stateOfView
     * the state of view is to Determine who called this method.
     * @return a flow of  state of Success operation  wrapped with DataState and list of workers.
     */
    fun getListOfMovies(
        stateOfView: StateOfView,
        pageNumber: Int
    ): Flow<DataState<MovieViewState>>




}
