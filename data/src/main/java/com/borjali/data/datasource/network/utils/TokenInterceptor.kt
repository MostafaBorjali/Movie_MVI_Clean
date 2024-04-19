package com.borjali.data.datasource.network.utils

import com.borjali.data.DataConstants.ACCEPT
import com.borjali.data.DataConstants.ANDROID
import com.borjali.data.DataConstants.APPLICATION_JSON
import com.borjali.data.DataConstants.AUTHORIZATION
import com.borjali.data.DataConstants.BEARER
import com.borjali.data.DataConstants.TOKEN
import com.borjali.data.DataConstants.X_PLATFORM
import okhttp3.*
import java.io.IOException
import javax.inject.Inject

class TokenInterceptor
@Inject
constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request: Request = chain.request()
        val requestBuilder = request.newBuilder()
        requestBuilder.addHeader(
            AUTHORIZATION,
            BEARER + TOKEN
        )
        requestBuilder.addHeader(X_PLATFORM, ANDROID)
        requestBuilder.addHeader(ACCEPT, APPLICATION_JSON)

        return chain.proceed(requestBuilder.build())
    }

}
