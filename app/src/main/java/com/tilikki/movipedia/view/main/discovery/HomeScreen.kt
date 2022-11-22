package com.tilikki.movipedia.view.main.discovery

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
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
import com.tilikki.movipedia.util.rememberFlow
import com.tilikki.movipedia.util.toPagingDataFlow
import com.tilikki.movipedia.view.navigation.Screens
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeScreen(
    navController: NavController,
    context: Context = LocalContext.current,
    viewModel: DiscoverMovieListViewModel = viewModel {
        DiscoverMovieListViewModel(AppSharedPreferences(context))
    }
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
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Featured movies",
                style = MaterialTheme.typography.h5,
            )
            IconButton(
                onClick = { navController.navigate(Screens.AppSettings.route) }
            ) {
                Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
            }
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