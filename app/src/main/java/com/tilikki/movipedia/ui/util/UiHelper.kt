package com.tilikki.movipedia.ui.util

import android.content.Context
import android.widget.Toast

fun throwInToast(
    context: Context,
    throwable: Throwable,
    message: String = "An error occurred! %%"
) {
    Toast.makeText(
        context,
        message.replace("%%", throwable.message.orEmpty()).trim(),
        Toast.LENGTH_SHORT
    ).show()
}