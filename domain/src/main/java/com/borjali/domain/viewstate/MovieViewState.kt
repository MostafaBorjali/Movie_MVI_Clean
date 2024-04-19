package com.borjali.domain.viewstate

import com.borjali.domain.model.MovieDto

/**
 * A data class representing the view state for the movie list screen.
 *
 * This data class holds the data required to represent the current state of the movie list UI.
 *
 * @property workers (default: empty list): The list of movie data transfer objects (DTOs) to be displayed.
 */
data class MovieViewState(
    val workers: List<MovieDto> = arrayListOf(),
)
