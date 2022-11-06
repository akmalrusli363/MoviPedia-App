package com.tilikki.movipedia.ui.component.generic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    onImeAction: (String) -> Unit = {}
) {
    var value by remember { mutableStateOf(searchText) }
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = value,
        onValueChange = {
            value = it
            onSearchTextChanged(it)
        },
        leadingIcon = { Icon(Icons.Filled.Search, null, tint = Color.Gray) },
        modifier = modifier
            .padding(4.dp)
            .background(getCardBackgroundColor(), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp),
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

@Preview
@Composable
private fun PreviewSearchBar() {
    SearchView(searchText = "wombo", placeholderText = "search")
}