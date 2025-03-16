package com.tilikki.movipedia.view.main

import android.R
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.graphics.toColorInt
import androidx.navigation.compose.rememberNavController
import com.tilikki.movipedia.ui.theme.MoviPediaTheme
import com.tilikki.movipedia.view.navigation.NavGraph

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(
                    R.attr.colorPrimary,
                    "#80808080".toColorInt())
            )
            MoviPediaTheme {
                val navController = rememberNavController()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    window.isNavigationBarContrastEnforced = false
                }

                NavGraph(navController)
            }
        }
    }
}
