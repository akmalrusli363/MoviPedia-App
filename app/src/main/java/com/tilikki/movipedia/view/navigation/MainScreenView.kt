package com.tilikki.movipedia.view.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun MainScreenView(navController: NavHostController, content: @Composable () -> Unit) {
    Scaffold(bottomBar = { HomeBottomNavigationBar(navController = navController) }) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colors.background,
            content = content
        )
    }
}