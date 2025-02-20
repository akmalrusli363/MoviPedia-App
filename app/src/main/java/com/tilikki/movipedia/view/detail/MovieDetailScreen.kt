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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.tilikki.movipedia.model.*
import com.tilikki.movipedia.model.general.Country
import com.tilikki.movipedia.ui.component.MovieNotFoundScreen
import com.tilikki.movipedia.ui.component.NavigableLoadingScreen
import com.tilikki.movipedia.ui.component.NavigableScreen
import com.tilikki.movipedia.ui.component.VideoList
import com.tilikki.movipedia.ui.component.subcomponent.*
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
    movieId: Int,
    navController: NavHostController? = null,
    viewModel: MovieDetailViewModel = viewModel()
) {
    if (movieId == Integer.MIN_VALUE) {
        val error = IllegalArgumentException("No movie ID provided!")
        Toast.makeText(LocalContext.current, error.message, Toast.LENGTH_LONG).show()
        NavigableScreen(navController, "Movie detail") {
            MovieNotFoundScreen(error)
        }
        return
    }
    val movieDetail = viewModel.movieDetail
    val isLoading = viewModel.isLoading
    LaunchedEffect(key1 = Unit) {
        viewModel.getMovieDetail(movieId)
    }
    if (isLoading) {
        NavigableLoadingScreen(
            navHostController = navController,
            title = "Getting movie information..."
        )
    } else if (movieDetail == null) {
        NavigableScreen(navController, "Movie detail") {
            MovieNotFoundScreen(null)
        }
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
private fun MovieDetailContent(
    movie: MovieDetail,
    navController: NavHostController? = null
) {
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
        if (movie.genres.isEmpty()) {
            Text(
                text = "Not available",
                modifier = Modifier.padding(horizontal = 4.dp),
                style = MaterialTheme.typography.body1,
                fontStyle = FontStyle.Italic,
            )
        } else {
            GenreChips(
                genres = movie.genres,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
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
        if (movie.releaseDate.isNotBlank()) {
            Text(
                text = movie.formatReleaseDate(SimpleDateFormat.LONG),
                modifier = Modifier.padding(horizontal = 4.dp),
                style = MaterialTheme.typography.body2
            )
        } else {
            Text(
                text = "Unknown date",
                modifier = Modifier.padding(horizontal = 4.dp),
                style = MaterialTheme.typography.body2,
                fontStyle = FontStyle.Italic
            )
        }
    }
    FieldWithChip(
        title = "Production Countries",
        list = movie.productionCountries,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) { countries ->
        CountryChips(countries = countries)
    }
    FieldWithChip(
        title = "Production Companies",
        list = movie.productionCompanies,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) { productionCompanies ->
        ProductionCompanyChips(productionCompanies = productionCompanies)
    }
    ConditionalComponent(obj = movie.keywords, movie.keywords.isNotEmpty()) {
        FieldWithChip(
            title = "Keywords",
            list = movie.keywords,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) { keywords ->
            KeywordChips(keywords = keywords)
        }
    }

    ConditionalComponent(string = movie.overview) {
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
    }

    ConditionalComponent(obj = movie.keywords, movie.keywords.isNotEmpty()) {
        Column(modifier = Modifier.padding(vertical = 4.dp)) {
            val sortedVideoList = movie.videos.partition { video ->
                video.type == VideoType.TEASER || video.type == VideoType.TRAILER
            }.toList().flatten()
            Text(
                text = "Videos",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(horizontal = 4.dp),
                fontWeight = FontWeight.Bold,
            )
            VideoList(videos = sortedVideoList)
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
private fun PreviewDetailScreenComplete() {
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
                    posterPath = "/3zXceNTtyj5FLjwQXuPvLYK5YYL.jpg",
                    keywords = listOf(
                        Keyword(123, "hello I am a fish"),
                        Keyword(143, "stairway to the heaven"),
                        Keyword(173, "dealing with crazy neighbourhood"),
                        Keyword(209, "spiderman"),
                        Keyword(233, "spiderman feat tom holland"),
                        Keyword(333, "underworld not the nether"),
                        Keyword(553, "hell on the highway"),
                    ),
                    videos = listOf(
                        Video(
                            id = "s1234567880",
                            name = "Wakanda Wakanda Wakanda",
                            type = VideoType.TEASER,
                            language = "",
                            country = "",
                            key = "RlOB3UALvrQ",
                            site = "YouTube",
                            resolution = 1080,
                            official = true,
                            publishedDate = "2022-10-06T13:00:01.000Z"
                        ),
                        Video(
                            id = "s1234567890",
                            name = "Wakanda - Teaser Video - even longer extended",
                            type = VideoType.TEASER,
                            language = "",
                            country = "",
                            key = "RlOB3UALvrQ",
                            site = "YouTube",
                            resolution = 1080,
                            official = true,
                            publishedDate = "2022-10-03T13:00:01.000Z"
                        ),
                        Video(
                            id = "s1234567899",
                            name = "Wakanda blooper: nippon paint",
                            type = VideoType.BLOOPERS,
                            language = "",
                            country = "",
                            key = "RlOB3UALvrQ",
                            site = "YouTube",
                            resolution = 1080,
                            official = true,
                            publishedDate = "2022-10-13T13:00:01.000Z"
                        ),
                    )
                )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDetail() {
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
private fun PreviewDetailScreenSimpleData() {
    MoviPediaTheme {
        Surface(color = MaterialTheme.colors.background) {
            MovieDetailContent(
                movie = MovieDetail(
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

@Preview
@Composable
private fun PreviewDetailScreenSimpleDataWithTeasers() {
    MoviPediaTheme {
        Surface(color = MaterialTheme.colors.background) {
            MovieDetailContent(
                movie = MovieDetail(
                    id = 1,
                    title = "title",
                    originalTitle = "title",
                    releaseDate = "11-12-2014",
                    genres = listOf(
                        Genre(13, "horror")
                    ),
                    videos = listOf(
                        Video(
                            id = "s1234567880",
                            name = "Wakanda Wakanda Wakanda",
                            type = VideoType.TEASER,
                            language = "",
                            country = "",
                            key = "RlOB3UALvrQ",
                            site = "YouTube",
                            resolution = 1080,
                            official = true,
                            publishedDate = "2022-10-06T13:00:01.000Z"
                        ),
                        Video(
                            id = "s1234567890",
                            name = "Wakanda - Teaser Video - even longer extended",
                            type = VideoType.TEASER,
                            language = "",
                            country = "",
                            key = "RlOB3UALvrQ",
                            site = "YouTube",
                            resolution = 1080,
                            official = true,
                            publishedDate = "2022-10-03T13:00:01.000Z"
                        ),
                        Video(
                            id = "s1234567899",
                            name = "Wakanda blooper: nippon paint",
                            type = VideoType.BLOOPERS,
                            language = "",
                            country = "",
                            key = "RlOB3UALvrQ",
                            site = "YouTube",
                            resolution = 1080,
                            official = true,
                            publishedDate = "2022-10-13T13:00:01.000Z"
                        ),
                    )
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
                    genres = listOf(),
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
