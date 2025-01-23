package com.chanu.photocache.core.model

sealed class CustomError(message: String?) : Exception(message) {
    data class ApiCustomError(
        val errorMessage: String?,
    ) : CustomError(errorMessage)

    data class NetWorkConnectCustomError(
        val errorMessage: String,
    ) : CustomError(errorMessage)

    data class TimeOutCustomError(
        val errorMessage: String,
    ) : CustomError(errorMessage)

    data class UnknownCustomError(
        val errorMessage: String,
    ) : CustomError(errorMessage)
}
