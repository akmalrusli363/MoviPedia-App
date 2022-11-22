package com.tilikki.movipedia.view.main.upcoming

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.tilikki.movipedia.util.rememberFlow
import com.tilikki.movipedia.util.toPagingDataFlow
import com.tilikki.movipedia.view.navigation.Screens
import kotlinx.coroutines.flow.Flow

@Composable
fun UpcomingMovieScreen(
    navController: NavController,
    context: Context = LocalContext.current,
    viewModel: UpcomingMovieViewModel = viewModel {
        UpcomingMovieViewModel(AppSharedPreferences(context))
    }
) {
    val movieList = rememberFlow(viewModel.movieList)
    UpcomingMovieContent(movieList = movieList, navController)
}

@Composable
private fun UpcomingMovieContent(
    movieList: Flow<PagingData<Movie>>,
    navController: NavController,
) {
    Column {
        Text(
            text = "Upcoming movies",
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
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
private fun PreviewUpcomingMovie() {
    val movieList = listOf<Movie>(
        Movie(id = 1, title = "Pen Pan Pencil", releaseDate = "2022-11-10"),
        Movie(id = 2, title = "Mobile isn't legend anymore", releaseDate = "2023-01-03"),
        Movie(id = 3, title = "Notepad", releaseDate = "2023-02-30"),
        Movie(id = 4, title = "Lost Delivery", releaseDate = "2023-03-20"),
    )

    MoviPediaTheme {
        UpcomingMovieContent(movieList.toPagingDataFlow(), rememberNavController())
    }
}