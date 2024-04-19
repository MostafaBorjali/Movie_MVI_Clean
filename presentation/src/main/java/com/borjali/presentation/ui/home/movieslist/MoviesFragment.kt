package com.borjali.presentation.ui.home.movieslist

import android.view.View.*
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.borjali.domain.model.MovieDto
import com.borjali.domain.model.event.EventErrorInternetConnection
import com.borjali.domain.model.event.EventListIsEmptyResult
import com.borjali.domain.utils.DataState
import com.borjali.domain.utils.EventOfCleanApp
import com.borjali.domain.utils.MessageType
import com.borjali.domain.viewstate.StateOfView.GetMovieList
import com.borjali.presentation.databinding.FragmentMoviesBinding
import com.borjali.presentation.extensions.isOnline
import com.borjali.presentation.extensions.snackMessage
import com.borjali.presentation.ui.base.BaseFragment
import com.borjali.presentation.ui.home.MovieEventState.GetMovies
import com.borjali.presentation.ui.home.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment :
    BaseFragment<FragmentMoviesBinding, MovieViewModel>(FragmentMoviesBinding::inflate) {
    private lateinit var adapter: MovieListAdapter
    private var pageNumber = 1
    private var listOfMovies: MutableList<MovieDto> = mutableListOf()

    /**
     * Subscribes to the relevant LiveData streams from the ViewModel and sets up event listeners.
     *
     * This method performs the following actions:
     *   1. Calls `initView()` to initialize the view components.
     *   2. Observes the `dataState` LiveData stream from the ViewModel using `viewLifecycleOwner`.
     *   3. Handles different states emitted by the `dataState` stream:
     *       - `SUCCESS`:
     *           - Checks the `stateOfView` type.
     *              - If it's `GetMovieList`, retrieves the list of workers from the `data` and populates the user recycler view using `fillUserRecyclerView`.
     *       - `ERROR`:
     *           - Hides the progress bar and shows the recycler view.
     *           - Extracts the error message from `dataState` and displays a snack bar using `snackMessage`.
     *       - `LOADING`:
     *           - Shows the progress bar and hides the recycler view and empty result view based on the `loading` flag.
     *   4. Registers an event listener for `EventListIsEmptyResult` using `EventOfCleanApp`.
     *      - Calls the `emptyResultHandle` function based on the emptiness of the list (`it.isEmpty`).
     *
     */
    override fun subscribeObservers() {
        initView()
        viewModel.dataState.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.SUCCESS -> {

                    when (dataState.stateOfView) {
                        is GetMovieList -> {
                            dataState.data?.workers?.let {
                                fillUserRecyclerView(it)
                            }
                        }
                        else -> {}
                    }

                }

                is DataState.ERROR -> {

                    binding.moviesProgressBar.visibility = GONE
                    binding.moviesRecyclerView.visibility = VISIBLE
                    dataState.stateMessage?.message?.let {
                        snackMessage(it, MessageType.ERROR)
                    }

                }

                is DataState.LOADING -> {
                    if (dataState.loading) {
                        binding.moviesRecyclerView.visibility = INVISIBLE
                        binding.emptyResultWorker.visibility = GONE
                        binding.moviesProgressBar.visibility = VISIBLE
                    }
                }

                else -> {}
            }


        }
        EventOfCleanApp.registerEvent(EventListIsEmptyResult::class.java) {
            emptyResultHandle(it.isEmpty)
        }
    }

    /**
     * Initializes the view components and sets up listeners for data loading and pagination.
     *
     * This method performs the following actions:
     *   1. Sets the visibility of the progress bar to `VISIBLE` to indicate loading.
     *   2. Calls `getListOfMovie()` to fetch the initial list of workers (movies in your context).
     *   3. Calls `searchMoviesByTitle()` (presumably for search functionality).
     *   4. Adds a scroll listener to the `moviesRecyclerView`:
     *       - In the `onScrollStateChanged` callback:
     *           - Checks if the user has scrolled to the bottom of the list (`!recyclerView.canScrollVertically(1)`)
     *             and if the search input is empty (`binding.searchInput.text.isNullOrEmpty()`).
     *           - If both conditions are met, it increments the `pageNumber` and calls `getListOfMovie()` to load more data (pagination).
     */
    private fun initView() {
        binding.moviesProgressBar.visibility = VISIBLE
        getListOfMovie()
        searchMoviesByTitle()
        binding.moviesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && binding.searchInput.text.isNullOrEmpty()) {
                    pageNumber = pageNumber.plus(1)
                    getListOfMovie()
                }
            }
        })
    }

    /**
     * search movies in loaded list
     */
    private fun searchMoviesByTitle() {
        binding.searchInput.doOnTextChanged { text, _, _, _ ->
            if (this::adapter.isInitialized)
                adapter.filter(text.toString())

        }
    }

    /**
     * set event for get list of Movies
     */
    private fun getListOfMovie() {
        if (requireContext().isOnline) {
            viewModel.setEventState(GetMovies(GetMovieList, pageNumber))
            binding.searchInput.text.clear()
        } else {
            EventOfCleanApp.send(EventErrorInternetConnection)
            binding.moviesProgressBar.visibility = GONE
        }
    }

    /**
     * Populates the user recycler view with a list of movie data transfer objects (DTOs).
     *
     * This method performs the following actions:
     *   1. Adds the provided list of `movies` to the `listOfMovies` property.
     *   2. Checks if the total movie count (`listOfMovies.size`) is less than or equal to 20:
     *       - Calls `emptyResultHandle` to handle potential empty list scenarios based on the combined list emptiness (`listOfMovies.isEmpty()`) after adding the new movies.
     *       - Creates a new `MovieListAdapter` instance with the updated `listOfMovies`.
     *       - Sets the adapter for the `moviesRecyclerView`.
     *   3. If the total movie count is greater than 20:
     *       - Notifies the adapter of a new item inserted at the last position (`listOfMovies.size.minus(1)`) to update the recycler view efficiently.
     *       - Hides the progress bar after data loading is complete.
     *   4. Sets the visibility of the `moviesRecyclerView` to `VISIBLE` to display the data.
     *
     * @param movies The list of movie DTOs to be added to the recycler view adapter.
     */
    private fun fillUserRecyclerView(movies: List<MovieDto>) {
        listOfMovies.addAll(movies)

        if (listOfMovies.size <= 20) {
            emptyResultHandle(listOfMovies.isEmpty())
            adapter = MovieListAdapter(listOfMovies)
            binding.moviesRecyclerView.adapter = adapter
        } else {
            adapter.notifyItemInserted(listOfMovies.size.minus(1))
            binding.moviesProgressBar.visibility = GONE
        }


        binding.moviesRecyclerView.visibility = VISIBLE
    }

    /**
     * show empty result list
     * @param empty for handle empty view
     */
    private fun emptyResultHandle(empty: Boolean) {
        binding.moviesProgressBar.visibility = GONE
        if (empty) {
            binding.moviesRecyclerView.visibility = GONE
            binding.emptyResultWorker.visibility = VISIBLE
        } else {
            binding.moviesRecyclerView.visibility = VISIBLE
            binding.emptyResultWorker.visibility = GONE

        }
    }


}