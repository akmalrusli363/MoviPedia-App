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
fun TopRatedMovieScreen(viewModel: MainViewModel) {
    val movieList by viewModel.topRatedMovieList.observeAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getTopRatedMovieList()
    }

    TopRatedMovieContent(movieList = movieList ?: emptyList())
}

@Composable
private fun TopRatedMovieContent(movieList: List<Movie>) {
    Column {
        Text(
            text = "Top rated movies",
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
private fun PreviewTopRatedMovie() {
    val movieList = listOf<Movie>(
        Movie(id = 1, title = "Pen Pan Pencil", releaseDate = "2022-11-10"),
        Movie(id = 2, title = "Mobile isn't legend anymore", releaseDate = "2023-01-03"),
        Movie(id = 3, title = "Notepad", releaseDate = "2023-02-30"),
        Movie(id = 4, title = "Lost Delivery", releaseDate = "2023-03-20"),
    )

    MoviPediaTheme {
        TopRatedMovieContent(movieList)
    }
}