package com.tilikki.movipedia.view.detail

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.tilikki.movipedia.R
import com.tilikki.movipedia.model.Genre
import com.tilikki.movipedia.model.MovieDetail
import com.tilikki.movipedia.ui.component.ErrorScreen
import com.tilikki.movipedia.ui.component.LoadingScreen
import com.tilikki.movipedia.ui.component.MovieNotFoundScreen
import com.tilikki.movipedia.ui.theme.MoviPediaTheme
import com.tilikki.movipedia.ui.theme.WhiteAlt
import com.tilikki.movipedia.ui.util.DotSeparator
import com.tilikki.movipedia.util.ConditionalComponent
import com.tilikki.movipedia.util.generateLoremIpsumString
import java.text.SimpleDateFormat

class MovieDetailActivity : ComponentActivity() {

    private val viewModel: MovieDetailViewModel by lazy {
        ViewModelProvider(this)[MovieDetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieId: Int = try {
            requireMovieId()
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            Log.e("MvFetcher", e.message, e)
            finish()
            throw e
        }

        setContent {
            MoviPediaTheme {
                // A surface container using the 'background' color from the theme
                MovieDetailScreen(viewModel, movieId)
            }
        }
    }

    private fun requireMovieId(): Int {
        val movieId = intent.getIntExtra("movie_id", Integer.MIN_VALUE)
        Log.d("MvFetch", "fetch movie id $movieId")
        if (movieId == Integer.MIN_VALUE) {
            throw IllegalArgumentException("No movie ID provided!")
        }
        return movieId
    }
}

@Composable
fun MovieDetailScreen(viewModel: MovieDetailViewModel, movieId: Int) {
    val movieDetail by viewModel.movieDetail.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState()
    LaunchedEffect(key1 = Unit) {
        viewModel.getMovieDetail(movieId)
    }
    if (isLoading != false) {
        LoadingScreen()
    } else if (movieDetail == null) {
        MovieNotFoundScreen(null)
    } else {
        MovieDetailContent(movie = movieDetail!!)
    }
}

@Composable
private fun MovieDetailContent(movie: MovieDetail) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = movie.title) }
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
            Column {
                AsyncImage(
                    model = movie.generateBackdropPath(),
                    placeholder = ColorPainter(Color.Gray),
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(CenterVertically)
                        .align(Alignment.CenterHorizontally)
                        .defaultMinSize(minHeight = 120.dp),
                    contentScale = ContentScale.FillWidth
                )
                Column(modifier = Modifier.padding(16.dp)) {
                    AsyncImage(
                        model = movie.generatePosterPath(),
                        placeholder = ColorPainter(Color.Gray),
                        contentDescription = movie.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .wrapContentSize()
                            .padding(bottom = 4.dp)
                            .defaultMinSize(minHeight = 120.dp)
                            .align(Alignment.CenterHorizontally),
                        contentScale = ContentScale.Fit
                    )
                    Text(
                        text = movie.title,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.h6
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
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.body1
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
                    Card(
                        backgroundColor = WhiteAlt,
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
                                color = Color.DarkGray,
                                style = MaterialTheme.typography.body2
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GenreChips(
    genres: List<Genre>,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.LightGray,
    shape: Shape = RoundedCornerShape(8.dp)
) {
    LazyRow(modifier = modifier) {
        items(genres) { genre ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .background(color = backgroundColor, shape = shape)
                    .padding(horizontal = 4.dp)
            ) {
                Text(
                    text = genre.name,
                    modifier = Modifier
                        .padding(4.dp),
                    style = MaterialTheme.typography.body2
                )
            }
        }
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

@Preview
@Composable
fun PreviewGenreChips() {
    Column(modifier = Modifier.fillMaxWidth()) {
        GenreChips(
            genres = listOf(
                Genre(1, "lmao"),
                Genre(2, "adventure"),
                Genre(3, "romance"),
            )
        )
    }
}

@Preview
@Composable
fun PreviewGenreChipsOverflow() {
    Column(modifier = Modifier.fillMaxWidth()) {
        GenreChips(
            genres = listOf(
                Genre(1, "lmao"),
                Genre(2, "adventure"),
                Genre(3, "romance"),
                Genre(4, "k-drama"),
                Genre(5, "anime"),
                Genre(6, "family friendly"),
            )
        )
    }
}