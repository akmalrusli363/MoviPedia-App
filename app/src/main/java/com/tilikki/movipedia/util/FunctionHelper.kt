package com.tilikki.movipedia.util

inline fun <T> T.runIf(condition: Boolean, block: T.() -> T): T {
    if (condition) {
        return this.run { block(this) }
    }
    return this
}

inline fun <T, K> T.runIfNotNull(obj: K?, block: T.(K) -> T): T {
    return if (obj != null) {
        this.run { block(obj) }
    } else {
        this
    }
}

/**
 * Get index of an item in the list, or return first index (0) if not found.
 */
fun <T> List<T>.indexOfOrFirstIndex(obj: T): Int {
    val index = this.indexOf(obj)
    return if (index < 0) 0 else index
}

/**
 * Get index of an item in the list using custom comparison, or return first index (0) if not found.
 */
fun <T> List<T>.indexOfOrFirstIndex(compare: (T) -> Boolean): Int {
    val index = this.indexOfFirst(compare)
    return if (index < 0) 0 else index
}
