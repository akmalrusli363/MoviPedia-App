package com.tilikki.movipedia.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tilikki.movipedia.view.main.HomeScreen
import com.tilikki.movipedia.view.main.MainViewModel
import com.tilikki.movipedia.view.main.UpcomingMovieScreen

@Composable
fun NavGraph (navController: NavHostController, mainViewModel: MainViewModel){
    NavHost(
        navController = navController,
        startDestination = Screens.FeaturedMovies.route)
    {
        composable(route = Screens.FeaturedMovies.route){
            HomeScreen(mainViewModel)
        }
        composable(route = Screens.UpcomingMovies.route){
            UpcomingMovieScreen(mainViewModel)
        }
    }
}