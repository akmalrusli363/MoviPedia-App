package com.tilikki.movipedia.view.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.tilikki.movipedia.view.ThemeEngineViewModel
import com.tilikki.movipedia.view.appinfo.AppInfoScreen
import com.tilikki.movipedia.view.detail.MovieDetailScreen
import com.tilikki.movipedia.view.main.catalogue.MovieByGenre
import com.tilikki.movipedia.view.main.discovery.HomeScreen
import com.tilikki.movipedia.view.main.search.MovieSearchScreen
import com.tilikki.movipedia.view.main.top_rated.TopRatedMovieScreen
import com.tilikki.movipedia.view.main.trending.TrendingMovieScreen
import com.tilikki.movipedia.view.main.upcoming.UpcomingMovieScreen

@Composable
fun NavGraph(navController: NavHostController, themeEngineViewModel: ThemeEngineViewModel) {
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
        composable(
            route = Screens.MovieListByGenre.route,
            arguments = listOf(navArgument(Screens.MovieListByGenre.GENRE_ID) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val genreId = backStackEntry.arguments?.getInt(Screens.MovieListByGenre.GENRE_ID)
                ?: Integer.MIN_VALUE
            MovieByGenre(genreId = genreId, navController = navController)
        }
        composable(route = Screens.AppSettings.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(500))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(500))
            }) {
            AppInfoScreen(navController = navController, themeViewModel = themeEngineViewModel)
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
        composable(route = Screens.SearchMovies.route) {
            MainScreenView(navController = navController) {
                MovieSearchScreen(navController)
            }
        }
    }
}