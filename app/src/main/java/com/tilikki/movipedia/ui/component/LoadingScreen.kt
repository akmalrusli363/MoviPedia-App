package com.tilikki.movipedia.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tilikki.movipedia.view.navigation.NavigationBackButton

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        LoadingBox()
    }
}

@Composable
fun LoadingBox(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(8.dp),
    ) {
        CircularProgressIndicator(modifier = Modifier.size(64.dp))
        Text(text = "Loading", modifier = Modifier.padding(vertical = 4.dp))
    }
}

@Composable
fun NavigableLoadingScreen(navHostController: NavHostController?) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.primary,
                title = { },
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
        LoadingScreen(modifier = Modifier.padding(it))
    }
}

@Preview
@Composable
fun PreviewLoadingScreen() {
    LoadingScreen()
}

@Preview
@Composable
fun PreviewNavLoadingScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.primary,
                title = { },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) {
        LoadingScreen(modifier = Modifier.padding(it))
    }
}