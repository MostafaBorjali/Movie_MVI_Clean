package com.borjali.android.di


import com.borjali.android.BuildConfig
import com.borjali.data.DataConstants.BASE_URL
import com.borjali.data.DataConstants.TIME_OUT_API
import com.borjali.data.datasource.network.api.CleanAppApi
import com.borjali.data.datasource.network.utils.TokenInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModules {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////  RETROFIT With Token  MODULE //////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Provides a configured OkHttpClient instance for making network requests.
     *
     * @param tokenInterceptor Interceptor for handling access tokens (implementation not shown).
     * @return A configured OkHttpClient instance.
     */
    @Singleton
    @Provides
    fun provideCleanAppClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            callTimeout(TIME_OUT_API, TimeUnit.SECONDS)
            connectTimeout(TIME_OUT_API, TimeUnit.SECONDS)
            writeTimeout(TIME_OUT_API, TimeUnit.SECONDS) // write timeout
            readTimeout(TIME_OUT_API, TimeUnit.SECONDS) // read timeout
            addInterceptor(tokenInterceptor)
            if (BuildConfig.DEBUG) {
                addNetworkInterceptor(StethoInterceptor())
                addNetworkInterceptor(
                    HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                )
            }
        }.build()
    }

    /**
     * Provides a Retrofit service interface for interacting with the CleanApp API.
     *
     * @param tokenInterceptor Interceptor for handling access tokens (implementation not shown).
     * @return An instance of the CleanAppApi interface.
     */
    @Singleton
    @Provides
    fun provideCleanAppRetrofitService(tokenInterceptor: TokenInterceptor):
            CleanAppApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(provideCleanAppClient(tokenInterceptor))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CleanAppApi::class.java)

}
