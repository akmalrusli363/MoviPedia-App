package com.tilikki.movipedia.util

fun <T> MutableList<T>.swapList(newList: List<T>) {
    clear()
    addAll(newList)
}