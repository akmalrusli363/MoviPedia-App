package com.tilikki.movipedia.view.main.trending

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.repository.AppSharedPreferences
import com.tilikki.movipedia.ui.component.PagingMovieListScreen
import com.tilikki.movipedia.ui.theme.MoviPediaTheme
import com.tilikki.movipedia.util.toPagingDataFlow
import com.tilikki.movipedia.view.navigation.Screens
import kotlinx.coroutines.flow.Flow

@Composable
fun TrendingMovieScreen(
    navController: NavController,
    context: Context = LocalContext.current,
    viewModel: TrendingMovieViewModel = viewModel {
        TrendingMovieViewModel(AppSharedPreferences(context))
    }
) {
    val movieList = remember { viewModel.movieList }
    TrendingMovieContent(movieList = movieList, navController)
}

@Composable
private fun TrendingMovieContent(
    movieList: Flow<PagingData<Movie>>,
    navController: NavController,
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Trending movies",
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .padding(16.dp)
            )
            Text(
                text = "WEEKLY",
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier
                    .padding(16.dp),
            )
        }
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
private fun PreviewTopRatedMovie() {
    val movieList = listOf<Movie>(
        Movie(id = 1, title = "Pen Pan Pencil", releaseDate = "2022-11-10"),
        Movie(id = 2, title = "Mobile isn't legend anymore", releaseDate = "2023-01-03"),
        Movie(id = 3, title = "Notepad", releaseDate = "2023-02-30"),
        Movie(id = 4, title = "Lost Delivery", releaseDate = "2023-03-20"),
    )

    MoviPediaTheme {
        TrendingMovieContent(movieList.toPagingDataFlow(), navController = rememberNavController())
    }
}