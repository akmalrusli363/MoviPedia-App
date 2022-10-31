package com.tilikki.movipedia.view.navigation

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import com.tilikki.movipedia.R

data class BarItem(
    val title: String,
    @DrawableRes val image: Int,
    val route: String
)

object HomeScreenNavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "Discover",
            image = R.drawable.ic_baseline_explore_24,
            route = "featured_movies"
        ),
        BarItem(
            title = "Upcoming Movies",
            image = R.drawable.ic_baseline_upcoming_24,
            route = "upcoming_movies"
        ),
    )
}