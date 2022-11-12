package com.tilikki.movipedia.ui.component.generic

import android.view.KeyEvent.ACTION_DOWN
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tilikki.movipedia.ui.theme.getCardBackgroundColor

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchView(
    searchText: String,
    modifier: Modifier = Modifier,
    placeholderText: String = "Search",
    onSearchTextChanged: (String) -> Unit = {},
    onImeAction: (String) -> Unit = {},
    onClearTextAction: (() -> Unit)? = null
) {
    var value by remember { mutableStateOf(searchText) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val enterKeyAction = { kEvent: KeyEvent ->
        if (kEvent.key == Key.Enter || kEvent.nativeKeyEvent.action == ACTION_DOWN) {
            keyboardController?.hide()
            onImeAction(value)
            true
        } else {
            false
        }
    }
    val clearButtonIcon: @Composable (() -> Unit)? =
        if (onClearTextAction != null && value.isNotBlank()) {
            {
                ClearSearchQueryButton {
                    value = ""
                    onClearTextAction()
                }
            }
        } else null
    TextField(
        value = value,
        onValueChange = {
            value = it
            onSearchTextChanged(it)
        },
        leadingIcon = { Icon(Icons.Filled.Search, null, tint = Color.Gray) },
        trailingIcon = clearButtonIcon,
        modifier = modifier
            .padding(4.dp)
            .background(getCardBackgroundColor(), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp)
            .onKeyEvent(enterKeyAction),
        placeholder = { Text(placeholderText) },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            cursorColor = Color.DarkGray
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            keyboardController?.hide()
            onImeAction(value)
        }),
    )
}

@Composable
private fun ClearSearchQueryButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Filled.Clear,
            contentDescription = "Clear search",
            tint = Color.Gray
        )
    }
}

@Preview
@Composable
private fun PreviewSearchBar() {
    SearchView(searchText = "wombo", placeholderText = "search")
}