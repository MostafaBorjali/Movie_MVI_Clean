package com.borjali.presentation.ui.home

import com.borjali.domain.viewstate.StateOfView

/**
 * set Event for All worker action
 */
sealed class MovieEventState {
    /**
     * set Event for get worker list
     */
    data class GetMovies(val stateOfView: StateOfView,val pageNumber: Int) : MovieEventState()



}
