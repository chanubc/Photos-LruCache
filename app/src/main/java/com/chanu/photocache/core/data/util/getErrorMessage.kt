package com.chanu.photocache.core.data.util

import com.chanu.photocache.core.model.CustomError
import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

private const val UNKNOWN_ERROR_MESSAGE = "Unknown error"
private const val NETWORK_CONNECT_ERROR_MESSAGE = "네트워크 연결이 원활하지 않습니다"
private const val INTERNET_CONNECTION_ERROR_MESSAGE = "인터넷에 연결해 주세요"
private const val TIMEOUT_ERROR_MESSAGE = "서버가 응답하지 않습니다"

private fun HttpException.getErrorMessage(): String {
    val errorBody = response()?.errorBody()?.string() ?: return UNKNOWN_ERROR_MESSAGE
    return parseErrorMessage(errorBody)
}

private fun parseErrorMessage(errorBody: String): String =
    try {
        JSONObject(errorBody).getString("message")
    } catch (e: Exception) {
        UNKNOWN_ERROR_MESSAGE
    }

fun Throwable.toCustomError(): Throwable = when (this) {
    is HttpException -> CustomError.ApiError(this.getErrorMessage())
    is UnknownHostException -> CustomError.NetWorkConnectError(NETWORK_CONNECT_ERROR_MESSAGE)
    is ConnectException -> CustomError.NetWorkConnectError(INTERNET_CONNECTION_ERROR_MESSAGE)
    is SocketTimeoutException -> CustomError.TimeOutError(TIMEOUT_ERROR_MESSAGE)
    is SocketException -> CustomError.SocketError(INTERNET_CONNECTION_ERROR_MESSAGE)
    else -> CustomError.UnknownError(this.message ?: UNKNOWN_ERROR_MESSAGE)
}

fun <T> Throwable.handleThrowable(): Result<T> = Result.failure(this.toCustomError())
