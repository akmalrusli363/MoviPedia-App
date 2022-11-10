package com.tilikki.movipedia.util

fun Throwable.asException(): Exception {
    return this as? Exception ?: Exception(this)
}