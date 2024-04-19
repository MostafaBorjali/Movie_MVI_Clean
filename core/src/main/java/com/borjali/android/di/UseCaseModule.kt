package com.borjali.android.di


import com.borjali.domain.repository.MovieRepository
import com.borjali.domain.usecase.movie.GetListOfMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * This module uses `@Provides` to provide an instance of the `GetListOfMoviesUseCase`.
 *
 * @Provides:
 *   - `GetListOfMoviesUseCase`: Injected with the `movieRepository` dependency.
 *     - Creates an instance of `GetListOfMoviesUseCase` using the provided `movieRepository`.
 *
 */
@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    
    @Provides
    fun provideGetListOfMoviesUseCaseUseCase(movieRepository: MovieRepository) =
        GetListOfMoviesUseCase(movieRepository)

}
