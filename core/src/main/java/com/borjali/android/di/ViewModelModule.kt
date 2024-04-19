package com.borjali.android.di


import com.borjali.domain.usecase.movie.GetListOfMoviesUseCase
import com.borjali.presentation.ui.home.MovieViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {

    @Provides
    @Singleton
    fun provideMovieViewModel(
        getListOfMoviesUseCase: GetListOfMoviesUseCase,
    ) = MovieViewModel(
        getListOfMoviesUseCase = getListOfMoviesUseCase,
    )

}
