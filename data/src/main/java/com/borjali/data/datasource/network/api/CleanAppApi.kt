@file:Suppress("KDocUnresolvedReference")

package com.borjali.data.datasource.network.api

import com.borjali.data.datasource.network.ApiTablesNames.getListOfMovies
import com.borjali.domain.model.MovieDto
import com.borjali.domain.model.base.ServerResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The main services that handles all endpoint processes with token
 */
interface CleanAppApi {
    /**
     * Fetches a list of movie data transfer objects (DTOs) from the server using a GET request.
     *
     * This suspend function performs a network call to the `/getListOfMovies` endpoint.
     *
     * @param includeAdult (Optional, default: false): Whether to include adult movies in the results.
     * @param includeVideo (Optional, default: false): Whether to include videos in the results.
     * @param language (Optional, default: "en-US"): The language to use for the results.
     * @param page (Required): The page number for pagination (starts from 1).
     * @param fromDate (Required): The start date for filtering movies based on primary release date in YYYY-MM-DD format.
     * @param toDate (Required): The end date for filtering movies based on primary release date in YYYY-MM-DD format.
     * @param sortBy (Optional, default: "popularity.desc"): The sort criteria for the results (e.g., "popularity.desc" for descending popularity).
     * @return A `ServerResponse<List<MovieDto>?>` object:
     *   - If successful, contains the list of `MovieDto` objects in the `data` field.
     *   - May contain an error message in the `errorMessage` field if the request fails.
     *
     * @throws CancellationException If the coroutine is cancelled.
     * @throws IOException If there's an error during the network operation.
     */
    @GET(getListOfMovies)
    suspend fun getListOfMovies(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
        @Query("primary_release_date.gte") fromDate: String,
        @Query("primary_release_date.lt") toDate:String,
        @Query("sort_by") sortBy: String = "popularity.desc"
    ): ServerResponse<List<MovieDto>?>


}
