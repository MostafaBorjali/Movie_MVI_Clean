package com.borjali.domain.usecase.movie

import com.borjali.domain.repository.MovieRepository
import com.borjali.domain.utils.DataState
import com.borjali.domain.viewstate.StateOfView
import com.borjali.domain.viewstate.MovieViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
/**
 * Use Case class for get worker list and retrieve data from worker screen .
 *
 * @param repository the instance of WorkerRepository.
 */
class GetListOfMoviesUseCase(private val repository: MovieRepository) {

    fun invoke(stateOfView: StateOfView,pageNumber: Int): Flow<DataState<MovieViewState>> {
        return repository.getListOfMovies(stateOfView,pageNumber).flowOn(IO)
    }
}
