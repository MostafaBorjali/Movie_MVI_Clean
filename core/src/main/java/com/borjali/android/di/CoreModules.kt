package com.borjali.android.di

import android.content.Context
import android.util.Log
import androidx.multidex.BuildConfig
import com.borjali.data.datasource.cache.preferences.CleanAppPreferencesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.borjali.domain.preference.CleanAppPreferences
import javax.inject.Singleton
import timber.log.Timber
/**
 * This module provides the following dependencies:
 *   - `CleanAppPreferences`: Provides access to secure preferences for storing application data.
 *     - Injects the application context to create the `CleanAppPreferencesImpl` instance.
 *   - `Timber.Tree`: Provides a custom Timber tree for logging.
 *     - Creates an anonymous subclass of `Timber.DebugTree` that filters logs based on build variant
 *       and log level. Logs are only printed in debug builds or for INFO level and above.
 *   - `Context`: Provides the application context.
 *     - Injects the `@ApplicationContext` annotated context parameter.
 */
@Module
@InstallIn(SingletonComponent::class)
class CoreModules {
    @Provides
    fun provideSecurePreferences(context: Context): CleanAppPreferences =
       CleanAppPreferencesImpl(context)

    @Provides
    @Singleton
    fun provideTimberTree(): Timber.Tree =
        object : Timber.DebugTree() {
            override fun isLoggable(tag: String?, priority: Int) =
                BuildConfig.DEBUG || priority >= Log.INFO
        }

    @Provides
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }


}
