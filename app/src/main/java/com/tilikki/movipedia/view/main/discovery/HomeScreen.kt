package com.tilikki.movipedia.view.main.discovery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.ui.component.PagingMovieListScreen
import com.tilikki.movipedia.ui.theme.MoviPediaTheme
import com.tilikki.movipedia.util.rememberFlow
import com.tilikki.movipedia.util.toPagingDataFlow
import com.tilikki.movipedia.view.navigation.Screens
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: DiscoverMovieListViewModel = viewModel()
) {
    val movieList = rememberFlow(viewModel.movieList)
    HomeScreenContent(movieList = movieList, navController)
}

@Composable
private fun HomeScreenContent(
    movieList: Flow<PagingData<Movie>>,
    navController: NavController,
) {
    Column {
        Text(
            text = "Featured movies",
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
private fun DefaultPreview() {
    val movieList = listOf<Movie>(
        Movie(id = 1, title = "Out Little Land", releaseDate = "2022-10-10"),
        Movie(id = 2, title = "My Big Pan", releaseDate = "2022-05-03"),
        Movie(id = 3, title = "Pig Pork is Pork", releaseDate = "2022-03-20")
    )

    MoviPediaTheme {
        HomeScreenContent(movieList.toPagingDataFlow(), rememberNavController())
    }
}