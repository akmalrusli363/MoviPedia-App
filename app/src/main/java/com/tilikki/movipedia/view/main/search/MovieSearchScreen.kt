package com.tilikki.movipedia.view.main.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.ui.component.MovieList
import com.tilikki.movipedia.ui.component.subcomponent.SearchView
import com.tilikki.movipedia.ui.theme.MoviPediaTheme
import com.tilikki.movipedia.view.navigation.Screens

@Composable
fun MovieSearchScreen(
    navController: NavController,
    viewModel: MovieSearchViewModel = viewModel()
) {
    val movieList = remember { viewModel.movieList }
    val onSearch: (String) -> Unit = { query ->
        viewModel.debouncedSearchOnType(query)
    }
    MovieSearchContent(movieList = movieList, navController, onSearch)
}

@Composable
fun MovieSearchContent(
    movieList: List<Movie>?,
    navController: NavController,
    onSearch: (String) -> Unit
) {
    Column() {
        Text(
            text = "Search",
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .padding(16.dp)
        )
        SearchView(
            searchText = "",
            placeholderText = "Search for movies",
            onImeAction = onSearch,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        if (movieList != null) {
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
private fun PreviewMovieSearchScreenInitialState() {
    val movieList = null
    MoviPediaTheme {
        MovieSearchContent(movieList, navController = rememberNavController(), onSearch = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMovieSearchScreen() {
    val movieList = listOf<Movie>(
        Movie(id = 1, title = "Pen Pan Pencil", releaseDate = "2022-11-10"),
        Movie(id = 2, title = "Mobile isn't legend anymore", releaseDate = "2023-01-03"),
        Movie(id = 3, title = "Notepad", releaseDate = "2023-02-30"),
        Movie(id = 4, title = "Lost Delivery", releaseDate = "2023-03-20"),
    )

    MoviPediaTheme {
        MovieSearchContent(movieList, navController = rememberNavController(), onSearch = {})
    }
}