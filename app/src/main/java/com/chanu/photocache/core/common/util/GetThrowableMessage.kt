package com.chanu.photocache.core.common.util

import com.chanu.photocache.core.model.CustomError

fun getThrowableMessage(throwable: Throwable?): String = when (throwable) {
    is CustomError.UnknownError -> throwable.message.toString()
    is CustomError.NetWorkConnectError -> throwable.message.toString()
    is CustomError.ApiError -> throwable.message.toString()
    is CustomError.TimeOutError -> throwable.message.toString()
    else -> throwable?.message.toString()
}
