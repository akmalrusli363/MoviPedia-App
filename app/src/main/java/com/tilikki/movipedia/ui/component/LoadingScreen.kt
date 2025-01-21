package com.tilikki.movipedia.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun LoadingScreen(modifier: Modifier = Modifier, description: String? = null) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        LoadingBox(description = description)
    }
}

@Composable
fun LoadingBox(modifier: Modifier = Modifier, description: String? = null) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(8.dp),
    ) {
        CircularProgressIndicator(modifier = Modifier.size(64.dp))
        if (!description.isNullOrBlank()) {
            Text(description)
        }
    }
}

@Composable
fun NavigableLoadingScreen(
    navHostController: NavHostController?,
    title: String = "",
) {
    NavigableScreen(navHostController, title) {
        LoadingScreen(modifier = Modifier.padding(it))
    }
}

@Preview
@Composable
private fun PreviewLoadingBox() {
    LoadingBox(modifier = Modifier.padding(8.dp))
}

@Preview
@Composable
private fun PreviewLoadingBoxWithText() {
    LoadingBox(description = "Loading...", modifier = Modifier.padding(8.dp))
}

@Preview
@Composable
private fun PreviewLoadingScreen() {
    LoadingScreen()
}

@Preview
@Composable
private fun PreviewLoadingScreenWithText() {
    LoadingScreen(description = "Loading...")
}
