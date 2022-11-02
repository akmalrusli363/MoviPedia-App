package com.tilikki.movipedia.view.navigation

import androidx.annotation.DrawableRes
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
            title = "Trending",
            image = R.drawable.ic_baseline_trending_24,
            route = "trending_movies"
        ),
        BarItem(
            title = "Top Rated",
            image = R.drawable.ic_baseline_star_24,
            route = "top_rated_movies"
        ),
        BarItem(
            title = "Upcoming",
            image = R.drawable.ic_baseline_upcoming_24,
            route = "upcoming_movies"
        ),
    )
}