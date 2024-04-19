package com.borjali.android.di


import com.borjali.domain.repository.MovieRepository
import com.borjali.domain.usecase.movie.GetListOfMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    
    @Provides
    fun provideGetListOfMoviesUseCaseUseCase(movieRepository: MovieRepository) =
        GetListOfMoviesUseCase(movieRepository)

}
