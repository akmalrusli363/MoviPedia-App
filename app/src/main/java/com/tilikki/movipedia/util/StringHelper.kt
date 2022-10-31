package com.tilikki.movipedia.util

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum

fun generateLoremIpsumString(words: Int): String {
    return LoremIpsum(100).values.joinToString(" ").replace("\n", " ")
}