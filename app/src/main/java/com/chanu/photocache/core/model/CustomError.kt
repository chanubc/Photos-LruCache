package com.chanu.photocache.core.model

sealed class CustomError(message: String?) : Exception(message) {
    data class ApiError(
        val errorMessage: String?,
    ) : CustomError(errorMessage)

    data class NetWorkConnectError(
        val errorMessage: String,
    ) : CustomError(errorMessage)

    data class TimeOutError(
        val errorMessage: String,
    ) : CustomError(errorMessage)

    data class SocketError(
        val errorMessage: String,
    ) : CustomError(errorMessage)

    data class UnknownError(
        val errorMessage: String,
    ) : CustomError(errorMessage)
}
