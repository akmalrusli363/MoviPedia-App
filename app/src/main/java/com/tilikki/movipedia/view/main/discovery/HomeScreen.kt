package com.tilikki.movipedia.view.main.discovery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.model.general.FetchState
import com.tilikki.movipedia.ui.component.LoadingScreen
import com.tilikki.movipedia.ui.component.MovieList
import com.tilikki.movipedia.ui.component.MovieNotFoundScreen
import com.tilikki.movipedia.ui.theme.MoviPediaTheme
import com.tilikki.movipedia.view.navigation.Screens

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: DiscoverMovieListViewModel = viewModel()
) {
    val movieList = viewModel.movieList
    val fetchState = viewModel.fetchState

    LaunchedEffect(key1 = Unit) {
        viewModel.getMovieList()
    }

    HomeScreenContent(movieList = movieList, navController, fetchState)
}

@Composable
private fun HomeScreenContent(
    movieList: List<Movie>,
    navController: NavController,
    fetchState: FetchState = FetchState.defaultState()
) {
    Column {
        Text(
            text = "Featured movies",
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        if (fetchState.isLoading) {
            LoadingScreen()
        } else if (fetchState.failException != null) {
            MovieNotFoundScreen(error = fetchState.failException)
        } else {
            MovieList(
                movieList = movieList,
                modifier = Modifier.padding(8.dp),
                onMovieCardItemClick = { movieId ->
                    Screens.MovieDetail.navigateTo(navController, movieId)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    val movieList = listOf<Movie>(
        Movie(id = 1, title = "Out Little Land", releaseDate = "2022-10-10"),
        Movie(id = 2, title = "My Big Pan", releaseDate = "2022-05-03"),
        Movie(id = 3, title = "Pig Pork is Pork", releaseDate = "2022-03-20")
    )

    MoviPediaTheme {
        HomeScreenContent(movieList, rememberNavController())
    }
}