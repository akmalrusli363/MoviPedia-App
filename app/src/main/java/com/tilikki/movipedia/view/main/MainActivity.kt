package com.tilikki.movipedia.view.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.navigation.compose.rememberNavController
import com.tilikki.movipedia.repository.AppSharedPreferences
import com.tilikki.movipedia.ui.theme.MoviPediaTheme
import com.tilikki.movipedia.view.navigation.NavGraph

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val sharedPrefs = AppSharedPreferences(this)
            MoviPediaTheme(darkTheme = sharedPrefs.isDarkMode(isSystemInDarkTheme())) {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }
}
