package com.tilikki.movipedia.util

import androidx.compose.runtime.Composable
import org.w3c.dom.Text

@Composable
fun ConditionalComponent(obj: Any?, component: @Composable () -> Unit) {
    if(obj != null) {
        component()
    }
}

@Composable
fun ConditionalComponent(obj: Any?, otherConditions: Boolean, component: @Composable () -> Unit) {
    if(obj != null && otherConditions) {
        component()
    }
}

@Composable
fun ConditionalComponent(string: String?, component: @Composable () -> Unit) {
    if(!string.isNullOrBlank()) {
        component()
    }
}

@Composable
fun ConditionalComponent(string: String?, otherConditions: Boolean, component: @Composable () -> Unit) {
    if(!string.isNullOrBlank() && otherConditions) {
        component()
    }
}