package com.tilikki.movipedia.ui.component

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tilikki.movipedia.model.Movie

@Composable
fun MovieList(
    movieList: List<Movie>,
    modifier: Modifier = Modifier,
    onMovieCardItemClick: (Int) -> Unit = {},
) {
    if (movieList.isEmpty()) {
        MovieNotFoundScreen(null)
    } else {
        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = modifier) {
            items(movieList) { movie ->
                MovieCard(movie = movie, onMovieCardClick = onMovieCardItemClick)
            }
        }
    }
}

@Preview(widthDp = 360)
@Composable
private fun ShowMovieList() {
    val movieList = listOf<Movie>(
        Movie(id = 1, title = "Out Little Land", releaseDate = "2022-10-10"),
        Movie(id = 2, title = "My Big Pan", releaseDate = "2022-05-03"),
        Movie(id = 3, title = "Pig Pork is Pork", releaseDate = "2022-03-20")
    )
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(movieList) { movie ->
            MovieCard(movie = movie)
        }
    }
}