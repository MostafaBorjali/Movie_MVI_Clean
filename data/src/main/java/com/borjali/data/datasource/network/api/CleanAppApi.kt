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
     * Get worker list
     * @return list of worker
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
