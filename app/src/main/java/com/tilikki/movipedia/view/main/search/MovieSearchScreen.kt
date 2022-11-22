package com.tilikki.movipedia.view.main.search

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.tilikki.movipedia.db.MovieDatabase
import com.tilikki.movipedia.model.Genre
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.repository.AppSharedPreferences
import com.tilikki.movipedia.ui.component.GenrePicker
import com.tilikki.movipedia.ui.component.PagingMovieListScreen
import com.tilikki.movipedia.ui.component.generic.SearchView
import com.tilikki.movipedia.ui.theme.MoviPediaTheme
import com.tilikki.movipedia.util.toPagingDataFlow
import com.tilikki.movipedia.view.navigation.Screens
import kotlinx.coroutines.flow.Flow

@Composable
fun MovieSearchScreen(
    navController: NavController,
    context: Context = LocalContext.current,
    viewModel: MovieSearchViewModel = viewModel(
        factory = MovieSearchViewModelFactory(
            MovieDatabase.getInstance(context),
            AppSharedPreferences(context)
        )
    )
) {
    val movieList = remember { viewModel.movieList }
    val genreList = remember { viewModel.genreList }
    val searchQuery = viewModel.searchQuery.collectAsState()
    val onSearch: (String) -> Unit = { query ->
        viewModel.searchMovieList(query)
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.getGenreList()
    }
    MovieSearchContent(
        movieList = movieList,
        genreList = genreList,
        isSearching = viewModel.isSearching,
        navController = navController,
        onSearch = onSearch,
        onClearSearch = { viewModel.isSearching = false },
        searchQuery = searchQuery.value
    )
}

@Composable
fun MovieSearchContent(
    movieList: Flow<PagingData<Movie>>,
    genreList: List<Genre>,
    isSearching: Boolean,
    navController: NavController,
    onSearch: (String) -> Unit,
    onClearSearch: () -> Unit = {},
    searchQuery: String = "",
) {
    Column() {
        Text(
            text = "Search",
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .padding(16.dp)
        )
        SearchView(
            searchText = searchQuery,
            placeholderText = "Search for movies",
            onImeAction = onSearch,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            onClearTextAction = onClearSearch,
        )
        if (isSearching) {
            PagingMovieListScreen(
                lazyMovieList = movieList.collectAsLazyPagingItems(),
                onMovieCardItemClick = { movieId ->
                    Screens.MovieDetail.navigateTo(navController, movieId)
                }
            )
        } else {
            GenrePicker(
                genres = genreList,
                modifier = Modifier.padding(8.dp),
                onGenreCardItemClicked = { genreId ->
                    Screens.MovieListByGenre.navigateTo(navController, genreId)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMovieSearchScreenInitialState() {
    val movieList = listOf<Movie>()
    val genreList = listOf(
        Genre(1, "lmao"),
        Genre(2, "adventure"),
        Genre(3, "romance"),
        Genre(4, "k-drama"),
        Genre(5, "anime"),
        Genre(6, "family friendly"),
        Genre(7, "bizarre"),
        Genre(8, "horror"),
    )
    MoviPediaTheme {
        MovieSearchContent(
            movieList = movieList.toPagingDataFlow(),
            genreList = genreList,
            isSearching = false,
            navController = rememberNavController(),
            onSearch = {})
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
    val genreList = listOf(
        Genre(1, "lmao"),
        Genre(2, "adventure"),
        Genre(3, "romance"),
        Genre(4, "k-drama"),
    )

    MoviPediaTheme {
        MovieSearchContent(
            movieList = movieList.toPagingDataFlow(),
            genreList = genreList,
            isSearching = true,
            navController = rememberNavController(),
            onSearch = {},
        )
    }
}