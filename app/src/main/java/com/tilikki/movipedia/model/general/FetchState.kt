package com.tilikki.movipedia.model.general

class FetchState private constructor(val isLoading: Boolean, val failException: Exception?) {
    companion object {
        fun defaultState() = FetchState(false, null)
        fun loading() = FetchState(true, null)
        fun successState() = FetchState(false, null)
        fun failedState(exception: Exception) = FetchState(false, exception)
    }
}
