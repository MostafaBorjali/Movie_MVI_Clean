package com.borjali.android.di

import com.borjali.data.repository.MovieRepositoryImpl
import com.borjali.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
/**
 * This module uses the `@Binds` annotation to bind the concrete implementation of the `MovieRepository`
 * interface to its interface type.
 *
 * Binding:
 *   - `MovieRepository`: Bound to the `MovieRepositoryImpl` class.
 */
@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)

interface RepositoryModule {

    @Binds
    fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

}
