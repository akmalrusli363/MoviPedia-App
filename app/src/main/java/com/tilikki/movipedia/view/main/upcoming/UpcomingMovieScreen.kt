package com.tilikki.movipedia.view.main.upcoming

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
fun UpcomingMovieScreen(
    navController: NavController,
    viewModel: UpcomingMovieViewModel = viewModel()
) {
    val movieList = remember { viewModel.movieList }
    val fetchState = viewModel.fetchState
    LaunchedEffect(key1 = Unit) {
        viewModel.getMovieList()
    }
    UpcomingMovieContent(movieList = movieList, navController, fetchState)
}

@Composable
private fun UpcomingMovieContent(
    movieList: List<Movie>,
    navController: NavController,
    fetchState: FetchState = FetchState.defaultState()
) {
    Column {
        Text(
            text = "Upcoming movies",
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
private fun PreviewUpcomingMovie() {
    val movieList = listOf<Movie>(
        Movie(id = 1, title = "Pen Pan Pencil", releaseDate = "2022-11-10"),
        Movie(id = 2, title = "Mobile isn't legend anymore", releaseDate = "2023-01-03"),
        Movie(id = 3, title = "Notepad", releaseDate = "2023-02-30"),
        Movie(id = 4, title = "Lost Delivery", releaseDate = "2023-03-20"),
    )

    MoviPediaTheme {
        UpcomingMovieContent(movieList, rememberNavController())
    }
}