package com.tilikki.movipedia.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tilikki.movipedia.view.detail.MovieDetailScreen
import com.tilikki.movipedia.view.main.discovery.HomeScreen
import com.tilikki.movipedia.view.main.top_rated.TopRatedMovieScreen
import com.tilikki.movipedia.view.main.trending.TrendingMovieScreen
import com.tilikki.movipedia.view.main.upcoming.UpcomingMovieScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.MovieList.route
    )
    {
        movieList(navController)
        composable(
            route = Screens.MovieDetail.route,
            arguments = listOf(navArgument("movie_id") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movie_id") ?: Integer.MIN_VALUE
            MovieDetailScreen(movieId = movieId, navController = navController)
        }
    }
}

fun NavGraphBuilder.movieList(navController: NavHostController) {
    navigation(
        startDestination = Screens.FeaturedMovies.route,
        route = Screens.MovieList.route
    ) {
        composable(route = Screens.FeaturedMovies.route) {
            MainScreenView(navController = navController) {
                HomeScreen(navController)
            }
        }
        composable(route = Screens.TrendingMovies.route) {
            MainScreenView(navController = navController) {
                TrendingMovieScreen(navController)
            }
        }
        composable(route = Screens.UpcomingMovies.route) {
            MainScreenView(navController = navController) {
                UpcomingMovieScreen(navController)
            }
        }
        composable(route = Screens.TopRatedMovies.route) {
            MainScreenView(navController = navController) {
                TopRatedMovieScreen(navController)
            }
        }
    }
}