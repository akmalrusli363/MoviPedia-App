package com.tilikki.movipedia.view.main.catalogue

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.ui.component.LoadingScreen
import com.tilikki.movipedia.ui.component.MovieFetchErrorScreen
import com.tilikki.movipedia.ui.component.PagingMovieList
import com.tilikki.movipedia.ui.theme.MoviPediaTheme
import com.tilikki.movipedia.ui.util.throwInToast
import com.tilikki.movipedia.util.asException
import com.tilikki.movipedia.util.getErrors
import com.tilikki.movipedia.util.rememberFlow
import com.tilikki.movipedia.util.toPagingDataFlow
import com.tilikki.movipedia.view.navigation.NavigationBackButton
import com.tilikki.movipedia.view.navigation.Screens
import kotlinx.coroutines.flow.Flow

@Composable
fun MovieByGenre(
    genreId: Int,
    navController: NavController,
    viewModel: MovieByGenreViewModel = viewModel(factory = MovieByGenreViewModelFactory(genreId))
) {
    val movieList = rememberFlow(viewModel.movieList)
    MovieByCategoryScreen(
        category = "Genre",
        movieList = movieList,
        navController = navController,
    )
}

@Composable
private fun MovieByCategoryScreen(
    category: String,
    movieList: Flow<PagingData<Movie>>,
    navController: NavController,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = category) },
                navigationIcon = { NavigationBackButton(navController = navController) }
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colors.background
        ) {
            MovieByCategoryContent(
                category = category,
                movieList = movieList,
                navController = navController
            )
        }
    }
}

@Composable
private fun MovieByCategoryContent(
    category: String,
    movieList: Flow<PagingData<Movie>>,
    navController: NavController,
) {
    val lazyMovieList = movieList.collectAsLazyPagingItems()
    val loadState = lazyMovieList.loadState
    val isLoading = loadState.refresh is LoadState.Loading
    val errorState = loadState.getErrors()

    Column {
        Text(
            text = category,
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        if (isLoading) {
            LoadingScreen()
        } else if (errorState != null) {
            throwInToast(LocalContext.current, errorState.error)
            MovieFetchErrorScreen(
                error = errorState.error.asException(),
                onRetryAction = { lazyMovieList.retry() }
            )
        } else {
            PagingMovieList(
                lazyMovieList = movieList.collectAsLazyPagingItems(),
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
private fun PreviewMovieByGenre() {
    val movieList = listOf<Movie>(
        Movie(id = 1, title = "Pen Pan Pencil", releaseDate = "2022-11-10"),
        Movie(id = 2, title = "Mobile isn't legend anymore", releaseDate = "2023-01-03"),
        Movie(id = 3, title = "Notepad", releaseDate = "2023-02-30"),
        Movie(id = 4, title = "Lost Delivery", releaseDate = "2023-03-20"),
    )

    MoviPediaTheme {
        MovieByCategoryContent(
            category = "missingno",
            movieList = movieList.toPagingDataFlow(),
            navController = rememberNavController()
        )
    }
}