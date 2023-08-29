package com.tilikki.movipedia.ui.component

import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
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
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = modifier
        ) {
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
        Movie(id = 3, title = "Pig Pork is Pork", releaseDate = "2022-03-20"),
        Movie(id = 4, title = "Nobody loves me until you hate", releaseDate = "2021-01-04"),
        Movie(id = 5, title = "Ark", releaseDate = "2023-01-02")
    )
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
    ) {
        items(movieList) { movie ->
            MovieCard(movie = movie)
        }
    }
}