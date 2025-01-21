package com.tilikki.movipedia.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.tilikki.movipedia.view.navigation.NavigationBackButton

@Composable
fun NavigableScreen(
    navHostController: NavHostController?,
    title: String = "",
    contents: @Composable ((PaddingValues) -> Unit),
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.primary,
                title = {
                    Text(text = title)
                },
                navigationIcon = {
                    if (navHostController != null) {
                        NavigationBackButton(
                            navController = navHostController
                        )
                    }
                }
            )
        }
    ) {
        contents(it)
    }
}

@Preview
@Composable
private fun PreviewNavErrorScreen() {
    NavigableScreen(null, "Error") {
        MovieNotFoundScreen(error = null, modifier = Modifier.padding(it))
    }
}

@Preview
@Composable
private fun PreviewNavFetchErrorScreen() {
    NavigableScreen(null, "Fetch Error") {
        MovieFetchErrorScreen(error = null, modifier = Modifier.padding(it))
    }
}

@Preview
@Composable
private fun PreviewNavLoadingScreen() {
    NavigableScreen(null, "Loading") {
        LoadingScreen(modifier = Modifier.padding(it))
    }
}
