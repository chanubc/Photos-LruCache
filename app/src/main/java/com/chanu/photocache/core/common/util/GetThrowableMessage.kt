package com.chanu.photocache.core.common.util

import android.content.res.Resources
import com.chanu.photocache.R
import com.chanu.photocache.core.model.CustomError

fun getThrowableMessage(
    throwable: Throwable?, localContextResource: Resources,
): String = when (throwable) {
    is CustomError.UnknownError -> localContextResource.getString(R.string.error_message_unknown)
    else -> throwable?.message.toString()
}
