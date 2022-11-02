package com.tilikki.movipedia.view.detail

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.tilikki.movipedia.model.Genre
import com.tilikki.movipedia.model.MovieDetail
import com.tilikki.movipedia.model.ProductionCompany
import com.tilikki.movipedia.model.general.Country
import com.tilikki.movipedia.ui.component.MovieNotFoundScreen
import com.tilikki.movipedia.ui.component.NavigableLoadingScreen
import com.tilikki.movipedia.ui.component.subcomponent.CountryChips
import com.tilikki.movipedia.ui.component.subcomponent.GenreChips
import com.tilikki.movipedia.ui.component.subcomponent.ProductionCompanyChips
import com.tilikki.movipedia.ui.component.subcomponent.RatingCard
import com.tilikki.movipedia.ui.theme.MoviPediaTheme
import com.tilikki.movipedia.ui.theme.getCardBackgroundColor
import com.tilikki.movipedia.ui.theme.getParagraphTextColor
import com.tilikki.movipedia.ui.util.DotSeparator
import com.tilikki.movipedia.ui.util.OverlappingAsyncImage
import com.tilikki.movipedia.util.ConditionalComponent
import com.tilikki.movipedia.util.generateLoremIpsumString
import com.tilikki.movipedia.view.navigation.NavigationBackButton
import java.text.SimpleDateFormat

@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    movieId: Int,
    navController: NavHostController? = null
) {
    if (movieId == Integer.MIN_VALUE) {
        val error = IllegalArgumentException("No movie ID provided!")
        Toast.makeText(LocalContext.current, error.message, Toast.LENGTH_LONG).show()
        MovieNotFoundScreen(error)
        return
    }
    val movieDetail = viewModel.movieDetail
    val isLoading = viewModel.isLoading
    LaunchedEffect(key1 = Unit) {
        viewModel.getMovieDetail(movieId)
    }
    if (isLoading) {
        NavigableLoadingScreen(navHostController = navController)
    } else if (movieDetail == null) {
        MovieNotFoundScreen(null)
    } else {
        MovieDetailContent(movie = movieDetail, navController = navController)
    }
}

private fun backdropToScreenConstraint(): ConstraintSet {
    return ConstraintSet {
        val backdrop = createRefFor("image_backdrop")
        val detailScreen = createRefFor("detail_screen")

        constrain(backdrop) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(detailScreen) {
            top.linkTo(backdrop.top, 64.dp)
        }
    }
}

@Composable
private fun MovieDetailContent(movie: MovieDetail, navController: NavHostController? = null) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = movie.title) },
                navigationIcon = { NavigationBackButton(navController = navController) }
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState()),
            color = MaterialTheme.colors.background
        ) {
            ConstraintLayout(constraintSet = backdropToScreenConstraint()) {
                OverlappingAsyncImage(
                    model = movie.generateBackdropPath(),
                    placeholder = ColorPainter(Color.Gray),
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .defaultMinSize(minHeight = 120.dp)
                        .layoutId("image_backdrop"),
                    contentScale = ContentScale.FillWidth
                )
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .layoutId("detail_screen")
                ) {
                    InnerMovieDetailContent(movie)
                }
            }
        }
    }
}

@Composable
private fun InnerMovieDetailContent(movie: MovieDetail) {
    AsyncImage(
        model = movie.generatePosterPath(),
        placeholder = ColorPainter(Color.Gray),
        contentDescription = movie.title,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .wrapContentSize()
            .shadow(4.dp)
            .padding(bottom = 4.dp)
            .defaultMinSize(minHeight = 120.dp, minWidth = 80.dp),
        contentScale = ContentScale.Fit
    )
    Text(
        text = movie.title,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        style = MaterialTheme.typography.h5
    )
    ConditionalComponent(
        string = movie.title,
        otherConditions = movie.originalTitleEqualToTitle()
    ) {
        Text(
            text = movie.originalTitle,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.subtitle2,
            fontStyle = FontStyle.Italic
        )
    }
    ConditionalComponent(string = movie.tagline) {
        Text(
            text = movie.tagline,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.body1
        )
    }
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Genre:",
            modifier = Modifier.padding(end = 4.dp),
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Medium,
        )
        GenreChips(
            genres = movie.genres,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
    Row(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = movie.status,
            modifier = Modifier.padding(end = 4.dp),
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Bold
        )
        DotSeparator(Modifier.padding(horizontal = 4.dp))
        Text(
            text = movie.formatReleaseDate(SimpleDateFormat.LONG),
            modifier = Modifier.padding(horizontal = 4.dp),
            style = MaterialTheme.typography.body2
        )
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = "Production Countries:",
            modifier = Modifier.padding(vertical = 4.dp),
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Medium,
        )
        CountryChips(
            countries = movie.productionCountries
        )
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = "Production Companies:",
            modifier = Modifier.padding(vertical = 4.dp),
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Medium,
        )
        ProductionCompanyChips(
            productionCompanies = movie.productionCompanies
        )
    }
    Card(
        backgroundColor = getCardBackgroundColor(),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(horizontal = 0.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Synopsis",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = movie.overview,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
                color = getParagraphTextColor(),
                style = MaterialTheme.typography.body2
            )
        }
    }

    Card(
        backgroundColor = getCardBackgroundColor(),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(horizontal = 0.dp, vertical = 8.dp)
    ) {
        RatingCard(
            rating = movie.voteAverage, users = movie.voteCount, modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewDetailScreen() {
    MoviPediaTheme {
        Surface(color = MaterialTheme.colors.background) {
            MovieDetailContent(
                MovieDetail(
                    id = 1,
                    title = "title",
                    originalTitle = "judul",
                    releaseDate = "22-09-2020",
                    genres = listOf(
                        Genre(1, "comedy"),
                        Genre(2, "romance")
                    ),
                    productionCompanies = listOf(
                        ProductionCompany(1, "union", "sp", "")
                    ),
                    productionCountries = listOf(
                        Country("USA", "us")
                    ),
                    tagline = "film dunia",
                    language = "en",
                    status = "released",
                    overview = generateLoremIpsumString(100),
                    backdropPath = "/d6MhreFdMHONqX3iZlJGCF8UkIt.jpg",
                    posterPath = "/3zXceNTtyj5FLjwQXuPvLYK5YYL.jpg"
                )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDetailScreenMinimalData() {
    MoviPediaTheme {
        Surface(color = MaterialTheme.colors.background) {
            MovieDetailContent(
                MovieDetail(
                    id = 1,
                    title = "title",
                    originalTitle = "title",
                    releaseDate = "11-12-2014",
                    genres = listOf(
                        Genre(13, "horror")
                    ),
                )
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewDetailScreenDarkTheme() {
    MoviPediaTheme {
        Surface(color = MaterialTheme.colors.background) {
            MovieDetailContent(
                MovieDetail(
                    id = 1,
                    title = "title",
                    originalTitle = "judul",
                    releaseDate = "22-09-2020",
                    genres = listOf(
                        Genre(1, "comedy"),
                        Genre(2, "romance")
                    ),
                    productionCompanies = listOf(
                        ProductionCompany(1, "union", "sp", "")
                    ),
                    productionCountries = listOf(
                        Country("USA", "us"), Country("Korea", "kr")
                    ),
                    tagline = "film dunia",
                    language = "en",
                    status = "released",
                    overview = generateLoremIpsumString(20),
                    backdropPath = "/d6MhreFdMHONqX3iZlJGCF8UkIt.jpg",
                    posterPath = "/3zXceNTtyj5FLjwQXuPvLYK5YYL.jpg"
                )
            )
        }
    }
}
