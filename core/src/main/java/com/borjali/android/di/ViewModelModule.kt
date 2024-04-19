package com.borjali.android.di


import com.borjali.domain.usecase.movie.GetListOfMoviesUseCase
import com.borjali.presentation.ui.home.MovieViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

/**
 * This module uses `@Provides` to provide an instance of the `MovieViewModel` with singleton scope.
 *
 * @Provides:
 *   - `MovieViewModel` (Singleton): Injected with the `getListOfMoviesUseCase` dependency.
 *   - Creates an instance of `MovieViewModel` using the provided `getListOfMoviesUseCase`.
 *
 */
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
