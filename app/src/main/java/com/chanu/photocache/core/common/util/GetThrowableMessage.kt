package com.chanu.photocache.core.common.util

import com.chanu.photocache.core.model.CustomError

fun getThrowableMessage(throwable: Throwable?): String = when (throwable) {
    is CustomError.UnknownCustomError -> throwable.message.toString()
    is CustomError.NetWorkConnectCustomError -> throwable.message.toString()
    is CustomError.ApiCustomError -> throwable.message.toString()
    is CustomError.TimeOutCustomError -> throwable.message.toString()
    else -> throwable?.message.toString()
}
