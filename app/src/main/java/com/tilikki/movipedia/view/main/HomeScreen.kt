package com.tilikki.movipedia.view.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.ui.component.MovieList
import com.tilikki.movipedia.ui.theme.MoviPediaTheme

@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val movieList by viewModel.movieList.observeAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getMovieList()
    }

    HomeScreenContent(movieList = movieList ?: emptyList())
}

@Composable
private fun HomeScreenContent(movieList: List<Movie>) {
    Column {
        Text(
            text = "Featured movies",
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        MovieList(
            movieList = movieList,
            modifier = Modifier.padding(8.dp)
        )
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
        HomeScreenContent(movieList)
    }
}