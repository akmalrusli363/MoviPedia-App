package com.tilikki.movipedia.view.navigation

sealed class Screens(val route:String) {
    object FeaturedMovies : Screens("featured_movies")
    object UpcomingMovies : Screens("upcoming_movies")
    object TopRatedMovies : Screens("top_rated_movies")
    object MovieDetail : Screens("movie_detail")
}
