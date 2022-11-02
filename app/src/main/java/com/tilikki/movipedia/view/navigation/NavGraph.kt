package com.tilikki.movipedia.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tilikki.movipedia.view.detail.MovieDetailScreen
import com.tilikki.movipedia.view.main.HomeScreen
import com.tilikki.movipedia.view.main.MainViewModel
import com.tilikki.movipedia.view.main.TopRatedMovieScreen
import com.tilikki.movipedia.view.main.UpcomingMovieScreen

@Composable
fun NavGraph(navController: NavHostController, mainViewModel: MainViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screens.FeaturedMovies.route
    )
    {
        composable(route = Screens.FeaturedMovies.route) {
            MainScreenView(navController = navController) {
                HomeScreen(mainViewModel, navController)
            }
        }
        composable(route = Screens.UpcomingMovies.route) {
            MainScreenView(navController = navController) {
                UpcomingMovieScreen(mainViewModel)
            }
        }
        composable(route = Screens.TopRatedMovies.route) {
            MainScreenView(navController = navController) {
                TopRatedMovieScreen(mainViewModel)
            }
        }
        composable(
            route = Screens.MovieDetail.route,
            arguments = listOf(navArgument("movie_id") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movie_id") ?: Integer.MIN_VALUE
            MovieDetailScreen(movieId = movieId, navController = navController)
        }
    }
}