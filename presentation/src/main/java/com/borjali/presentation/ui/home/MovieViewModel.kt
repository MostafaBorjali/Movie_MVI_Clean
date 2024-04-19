package com.borjali.presentation.ui.home

import dagger.hilt.android.lifecycle.HiltViewModel
import com.borjali.domain.usecase.movie.GetListOfMoviesUseCase
import com.borjali.domain.viewstate.MovieViewState
import com.borjali.presentation.ui.base.BaseViewModel
import com.borjali.presentation.ui.home.MovieEventState.*
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * shared view model for handle all worker action
 */
@HiltViewModel
class MovieViewModel
@Inject
constructor(
    private val getListOfMoviesUseCase: GetListOfMoviesUseCase,

    ) :
    BaseViewModel<MovieEventState, MovieViewState>() {

    override fun handleEventState(eventState: MovieEventState) =
        flow {
            when (eventState) {
                is GetMovies -> emitAll(
                    getListOfMoviesUseCase.invoke(
                        eventState.stateOfView,eventState.pageNumber
                    )
                )
            }
        }
}
