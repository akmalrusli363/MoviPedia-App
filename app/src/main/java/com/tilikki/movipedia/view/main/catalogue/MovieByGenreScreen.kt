package com.tilikki.movipedia.view.main.catalogue

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.tilikki.movipedia.db.MovieDatabase
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.repository.AppSharedPreferences
import com.tilikki.movipedia.ui.component.PagingMovieListScreen
import com.tilikki.movipedia.ui.theme.MoviPediaTheme
import com.tilikki.movipedia.util.rememberFlow
import com.tilikki.movipedia.util.toPagingDataFlow
import com.tilikki.movipedia.view.navigation.NavigationBackButton
import com.tilikki.movipedia.view.navigation.Screens
import kotlinx.coroutines.flow.Flow

@Composable
fun MovieByGenre(
    genreId: Int,
    navController: NavController,
    viewModel: MovieByGenreViewModel = viewModel(
        factory = MovieByGenreViewModelFactory(
            genreId = genreId,
            database = MovieDatabase.getInstance(LocalContext.current),
            sharedPreferences = AppSharedPreferences(LocalContext.current),
        )
    )
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchGenreNameFromDb()
    }
    val movieList = rememberFlow(viewModel.movieList)
    val genreName = rememberFlow(viewModel.genreName).collectAsState(initial = "").value
    val categoryLabeling = "Genre" + if (genreName.isNotBlank()) " - $genreName" else ""
    MovieByCategoryScreen(
        category = categoryLabeling,
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
                movieList = movieList,
                navController = navController
            )
        }
    }
}

@Composable
private fun MovieByCategoryContent(
    movieList: Flow<PagingData<Movie>>,
    navController: NavController,
) {
    Column {
        PagingMovieListScreen(
            lazyMovieList = movieList.collectAsLazyPagingItems(),
            onMovieCardItemClick = { movieId ->
                Screens.MovieDetail.navigateTo(navController, movieId)
            }
        )
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
        MovieByCategoryScreen(
            category = "missingno",
            movieList = movieList.toPagingDataFlow(),
            navController = rememberNavController()
        )
    }
}