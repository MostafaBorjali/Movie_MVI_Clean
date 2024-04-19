package com.borjali.data

/**
 * Data layer constants.
 */

object DataConstants {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////// USE Key shared preferences //////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    const val ACCESS_TOKEN = "access_token"
    const val REFRESH_TOKEN = "refresh_token"
    const val PREF_NAME = "movies.core_perf"

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////// USE Okhttp Header ///////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    const val BEARER = "Bearer "
    const val AUTHORIZATION = "Authorization"
    const val X_PLATFORM = "x-platform"
    const val ANDROID = "android"
    const val CONTENT_TYPE = "Content-Type"
    const val ACCEPT = "accept"
    const val ACCEPT_LANGUAGE = "Accept-Language"
    const val APPLICATION_JSON = "application/json"
    const val TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyZjdkNzFjOTgwYTA1N2Q2YTIwOWJkYzVkZGQ0NzY4ZCIsInN1YiI6IjY2MjFmZTdjZTRjOWViMDE2M2Y1Y2Q4YyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.-i6CsB5ruwkFML8s5PlO59uK9P54VIuY8lby9rjOZus"
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////// USE NetworkCall /////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    const val BASE_URL = "https://api.themoviedb.org"
    const val TIME_OUT_API = 10L

}