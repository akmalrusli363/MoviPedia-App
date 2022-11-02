package com.tilikki.movipedia.view.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

sealed class Screens(val route: String) {
    object FeaturedMovies : Screens("featured_movies")
    object UpcomingMovies : Screens("upcoming_movies")
    object TopRatedMovies : Screens("top_rated_movies")
    object MovieDetail : Screens("movie_detail/{movie_id}") {
        const val MOVIE_ID = "movie_id"
        fun routeByMovieId(movieId: Int): String {
            return "movie_detail/$movieId"
        }

        fun navigateTo(navController: NavController, movieId: Int) {
            navController.navigate(routeByMovieId(movieId)) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
            }
        }
    }
}
