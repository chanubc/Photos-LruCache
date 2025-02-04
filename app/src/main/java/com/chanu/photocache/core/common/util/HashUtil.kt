package com.chanu.photocache.core.common.util

import java.security.MessageDigest

fun String.toGenerateKey(): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val hashBytes = digest.digest(this.toByteArray(Charsets.UTF_8))
    return hashBytes.joinToString("") { "%02x".format(it) }
}
