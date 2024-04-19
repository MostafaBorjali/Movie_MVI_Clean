package com.borjali.data.datasource.network.utils

import com.borjali.domain.Constants.Companion.MESSAGE
import com.borjali.domain.Constants.Companion.NETWORK_ERROR
import com.borjali.domain.Constants.Companion.NETWORK_ERROR_TIMEOUT
import com.borjali.domain.Constants.Companion.UNKNOWN_ERROR
import com.borjali.domain.model.base.ServerResponse
import com.borjali.domain.utils.DataState
import com.borjali.domain.utils.MessageType
import com.borjali.domain.utils.StateMessage
import com.borjali.domain.utils.UIComponentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

/**
 * An abstract class that implements a pattern for fetching data from network and managing local cache.
 *
 * This class defines a template for handling network requests, updating cache, and emitting data states
 * for UI updates. It uses Flow for asynchronous operations and data state emission.
 *
 * @param <NetworkObj> The type of object retrieved from the network API.
 * @param <CacheObj> The type of object stored in the local cache.
 * @param <ViewState> The type of data used by the UI (e.g., view model state).
 *
 */
abstract class NetworkBoundResource<NetworkObj, CacheObj, ViewState>(
    private val apiCall: (suspend () -> ServerResponse<NetworkObj?>)? = null,

    ) {

    val result: Flow<DataState<ViewState>> = flow {
        emit(DataState.LOADING(isLoading = true))
        apiCall?.let {
            emitAll(safeAPICall())
        }
        emit(DataState.LOADING(isLoading = false))
    }

    private suspend fun safeAPICall() = flow {
        try {

            val result = apiCall?.invoke()
            if (result == null) {
                emit(
                    buildSnackBarError(

                        UNKNOWN_ERROR

                    )
                )
            } else {
                result.results?.let {
                    updateCache(it)
                }
                handleNetworkSuccess(result)?.let {
                    emit(it)
                }
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            when (throwable) {
                is TimeoutCancellationException -> {
                    emit(
                        buildSnackBarError(
                            NETWORK_ERROR_TIMEOUT

                        )
                    )

                }

                is IOException -> {
                    emit(
                        buildSnackBarError(

                            NETWORK_ERROR


                        )

                    )
                }

                is HttpException -> {
                    emit(buildSnackBarError(convertErrorBody(throwable)))
                }

                else -> {
                    emit(
                        buildSnackBarError(

                            UNKNOWN_ERROR

                        )
                    )
                }
            }
        }
    }.flowOn(Dispatchers.IO)


    private fun buildSnackBarError(
        message: String?
    ): DataState<ViewState> {
        return DataState.ERROR(
            stateMessage = StateMessage(
                message = message,
                uiComponentType = UIComponentType.SNACKBAR,
                messageType = MessageType.ERROR
            )
        )
    }

    private fun convertErrorBody(throwable: HttpException): String {
        return try {
            JSONObject(throwable.response()?.errorBody()?.charStream()?.readText().toString())
                .getString(MESSAGE)
                ?: UNKNOWN_ERROR

        } catch (exception: Exception) {

            UNKNOWN_ERROR

        }
    }

    open suspend fun handleNetworkSuccess(response: ServerResponse<NetworkObj?>?):
            DataState<ViewState>? {
        return null
    }


    open suspend fun updateCache(networkObject: NetworkObj) {}
}
