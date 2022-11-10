package com.tilikki.movipedia.ui.component

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.tilikki.movipedia.model.Movie
import kotlinx.coroutines.flow.Flow

@Composable
fun FlowableMovieList(
    movieList: Flow<PagingData<Movie>>,
    modifier: Modifier = Modifier,
    onMovieCardItemClick: (Int) -> Unit = {},
) {
    PagingMovieList(
        lazyMovieList = movieList.collectAsLazyPagingItems(),
        modifier = modifier,
        onMovieCardItemClick = onMovieCardItemClick
    )
}

@Composable
fun PagingMovieList(
    lazyMovieList: LazyPagingItems<Movie>,
    modifier: Modifier = Modifier,
    onMovieCardItemClick: (Int) -> Unit = {},
) {
    val loadState = lazyMovieList.loadState
    val isPrefetching = loadState.prepend is LoadState.Loading
    val isFetchMore = loadState.append is LoadState.Loading

    if (lazyMovieList.itemCount == 0) {
        MovieNotFoundScreen(null)
    } else {
        if (isPrefetching) {
            LoadingBox(modifier)
        }
        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = modifier) {
            items(lazyMovieList.itemCount) { mIndex ->
                lazyMovieList[mIndex]?.let {
                    MovieCard(
                        movie = it,
                        onMovieCardClick = onMovieCardItemClick
                    )
                }
            }
        }
        if (isFetchMore) {
            LoadingBox(modifier)
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