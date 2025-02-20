package com.tilikki.movipedia.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.tilikki.movipedia.helper.Constants
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.ui.util.throwInToast
import com.tilikki.movipedia.util.asException
import com.tilikki.movipedia.util.getErrors

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PagingMovieListScreen(
    lazyMovieList: LazyPagingItems<Movie>,
    onMovieCardItemClick: (Int) -> Unit = {},
) {
    val loadState = lazyMovieList.loadState
    val isLoading = loadState.refresh is LoadState.Loading
    val errorState = loadState.getErrors()
    val refreshMovieState = rememberPullRefreshState(isLoading, lazyMovieList::refresh)

    if (isLoading) {
        LoadingScreen()
    } else if (errorState != null) {
        throwInToast(LocalContext.current, errorState.error)
        MovieFetchErrorScreen(
            error = errorState.error.asException(),
            onRetryAction = { lazyMovieList.retry() }
        )
    } else {
        Box(modifier = Modifier.pullRefresh(refreshMovieState)) {
            StaggeredPagingMovieList(
                lazyMovieList = lazyMovieList,
                modifier = Modifier.padding(8.dp),
                onMovieCardItemClick = onMovieCardItemClick
            )
            PullRefreshIndicator(isLoading, refreshMovieState, Modifier.align(Alignment.TopCenter))
        }
    }
}

@Composable
fun PagingMovieList(
    lazyMovieList: LazyPagingItems<Movie>,
    modifier: Modifier = Modifier,
    onMovieCardItemClick: (Int) -> Unit = {},
) {
    val loadState = lazyMovieList.loadState
    val isFetchMore = loadState.append is LoadState.Loading

    if (lazyMovieList.itemCount == 0) {
        MovieNotFoundScreen(null)
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(Constants.MOVIE_LIST_GRID_COLUMNS),
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier,
        ) {
            items(lazyMovieList.itemCount) { mIndex ->
                lazyMovieList[mIndex]?.let {
                    MovieCard(
                        movie = it,
                        onMovieCardClick = onMovieCardItemClick
                    )
                }
            }
            item(span = { GridItemSpan(Constants.MOVIE_LIST_GRID_COLUMNS) }) {
                if (isFetchMore) {
                    LoadingBox(modifier)
                } else {
                    Text(
                        text = "End of movie list",
                        modifier = modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun StaggeredPagingMovieList(
    lazyMovieList: LazyPagingItems<Movie>,
    modifier: Modifier = Modifier,
    onMovieCardItemClick: (Int) -> Unit = {},
) {
    val loadState = lazyMovieList.loadState
    val isFetchMore = loadState.append is LoadState.Loading

    if (lazyMovieList.itemCount == 0) {
        MovieNotFoundScreen(null)
    } else {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = modifier,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                items(lazyMovieList.itemCount) { mIndex ->
                    lazyMovieList[mIndex]?.let {
                        MovieCard(
                            movie = it,
                            onMovieCardClick = onMovieCardItemClick
                        )
                    }
                }
                item {
                    if (isFetchMore) {
                        LoadingBox(modifier)
                    } else {
                        Text(
                            text = "End of movie list",
                            modifier = modifier.padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
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

@Preview(widthDp = 360)
@Composable
private fun ShowMovieListAppend() {
    val movieList = listOf(
        Movie(id = 1, title = "Out Little Land", releaseDate = "2022-10-10"),
        Movie(id = 2, title = "My Big Pan", releaseDate = "2022-05-03"),
        Movie(id = 3, title = "Pig Pork is Pork", releaseDate = "2022-03-20")
    )
    val modifier = Modifier.padding(16.dp)
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
        ) {
            items(movieList) { movie ->
                MovieCard(movie = movie)
            }
        }
        LoadingBox(modifier)
    }
}
