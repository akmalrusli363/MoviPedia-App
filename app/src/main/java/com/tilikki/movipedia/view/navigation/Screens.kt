package com.tilikki.movipedia.view.navigation

import androidx.navigation.NavController

sealed class Screens(val route: String) {
    object MovieList : Screens("movie_list")
    object FeaturedMovies : Screens("featured_movies")
    object TrendingMovies : Screens("trending_movies")
    object UpcomingMovies : Screens("upcoming_movies")
    object TopRatedMovies : Screens("top_rated_movies")
    object SearchMovies : Screens("search_movies")
    object MovieDetail : Screens("movie_detail/{movie_id}") {
        const val MOVIE_ID = "movie_id"
        private fun routeByMovieId(movieId: Int): String {
            return "movie_detail/$movieId"
        }

        fun navigateTo(navController: NavController, movieId: Int) {
            navController.navigate(routeByMovieId(movieId))
        }
    }
}
