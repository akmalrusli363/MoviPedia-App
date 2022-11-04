package com.tilikki.movipedia.view.navigation

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
import com.tilikki.movipedia.ui.util.TonedBottomNavigationBar

@Composable
fun HomeBottomNavigationBar(navController: NavHostController) {
    TonedBottomNavigationBar {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        HomeScreenNavBarItems.BarItems.forEach { navItem ->
            val isSelected = currentRoute == navItem.route
            BottomNavigationItem(
                selected = isSelected,
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
                    Icon(
                        painter = painterResource(id = navItem.image),
                        contentDescription = navItem.title
                    )
                },
                label = {
                    Text(text = navItem.title, softWrap = false)
                },
            )
        }
    }
}

@Preview
@Composable
private fun PreviewBottomNavBar() {
    HomeBottomNavigationBar(rememberNavController())
}