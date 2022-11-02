package com.tilikki.movipedia.view.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tilikki.movipedia.ui.theme.MoviPediaTheme
import com.tilikki.movipedia.ui.util.TonedBottomNavigationBar
import com.tilikki.movipedia.view.navigation.HomeScreenNavBarItems
import com.tilikki.movipedia.view.navigation.NavGraph

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviPediaTheme {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    TonedBottomNavigationBar {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        HomeScreenNavBarItems.BarItems.forEach { navItem ->
            BottomNavigationItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(painter = painterResource(id = navItem.image),
                        contentDescription = navItem.title)
                },
                label = {
                    Text(text = navItem.title)
                },
            )
        }
    }
}

@Preview
@Composable
private fun PreviewBottomNavBar() {
    BottomNavigationBar(rememberNavController())
}