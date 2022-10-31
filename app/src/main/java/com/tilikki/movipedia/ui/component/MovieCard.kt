package com.tilikki.movipedia.ui.component

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.ui.theme.WhiteAlt
import com.tilikki.movipedia.R
import com.tilikki.movipedia.view.detail.MovieDetailActivity
import java.text.SimpleDateFormat

@Composable
fun MovieCard(movie: Movie, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Card(backgroundColor = WhiteAlt, modifier = modifier.padding(8.dp).clickable {
        val intent = Intent(context, MovieDetailActivity::class.java).apply {
            this.putExtra("movie_id", movie.id)
        }
        context.startActivity(intent)
    }) {
        Column {
            AsyncImage(
                model = movie.generatePosterPath(),
                placeholder = painterResource(id = R.drawable.ic_baseline_local_movies_24),
                contentDescription = movie.title,
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .wrapContentSize()
                    .padding(bottom = 4.dp)
                    .defaultMinSize(minHeight = 120.dp, minWidth = 80.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.FillWidth
            )
            Text(
                text = movie.title,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                style = MaterialTheme.typography.h6
            )
            Text(
                text = movie.formatReleaseDate(SimpleDateFormat.LONG),
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 8.dp),
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Preview
@Composable
private fun ShowMovieCard() {
    val movie = Movie(id = 1, title = "Out Little Land", releaseDate = "2022-10-10")
    MovieCard(movie = movie)
}