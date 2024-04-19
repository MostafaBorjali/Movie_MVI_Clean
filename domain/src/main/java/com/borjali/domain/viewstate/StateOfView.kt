@file:Suppress("unused")

package com.borjali.domain.viewstate

/**
 * A sealed class representing different states of a view (potentially a movie list view).
 */

sealed class StateOfView {
    object None : StateOfView()
    object GetMovieList : StateOfView()

}
