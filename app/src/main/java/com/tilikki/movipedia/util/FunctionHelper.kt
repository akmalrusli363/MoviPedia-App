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
