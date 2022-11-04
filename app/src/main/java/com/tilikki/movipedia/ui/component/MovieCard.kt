package com.tilikki.movipedia.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tilikki.movipedia.R
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.ui.theme.Orange600
import com.tilikki.movipedia.ui.theme.getCardBackgroundColor
import java.text.SimpleDateFormat

@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier,
    onMovieCardClick: (Int) -> Unit = {},
) {
    Card(
        backgroundColor = getCardBackgroundColor(),
        modifier = modifier
            .padding(8.dp)
            .clickable { onMovieCardClick(movie.id) }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .align(Alignment.CenterHorizontally)
            ) {
                AsyncImage(
                    model = movie.generatePosterPath(),
                    placeholder = painterResource(id = R.drawable.ic_baseline_local_movies_24),
                    contentDescription = movie.title,
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .padding(bottom = 4.dp)
                        .defaultMinSize(minHeight = 120.dp, minWidth = 80.dp)
                        .align(Alignment.Center),
                    contentScale = ContentScale.FillWidth
                )
                RatingChipContent(
                    movie = movie,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                )
            }
            Column(
                modifier = Modifier.padding(
                    start = 8.dp,
                    end = 8.dp,
                    top = 2.dp,
                    bottom = 8.dp
                )
            ) {
                Text(
                    text = movie.title,
                    modifier = Modifier.padding(vertical = 2.dp),
                    style = MaterialTheme.typography.h6
                )
                if (movie.releaseDate.isNotBlank()) {
                    Text(
                        text = movie.formatReleaseDate(SimpleDateFormat.LONG),
                        modifier = Modifier.padding(vertical = 2.dp),
                        style = MaterialTheme.typography.caption
                    )
                } else {
                    Text(
                        text = "Unknown date",
                        modifier = Modifier.padding(vertical = 2.dp),
                        style = MaterialTheme.typography.caption,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}

@Composable
private fun RatingChipContent(movie: Movie, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(4.dp)
            .background(color = Color.DarkGray, shape = RoundedCornerShape(8.dp))
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val hasRating = movie.voteCount > 0

        @DrawableRes
        val starRes: Int = when {
            hasRating -> R.drawable.ic_baseline_star_24
            else -> R.drawable.ic_baseline_star_border_24
        }
        val score = when {
            hasRating -> String.format("%.0f%%", movie.voteAverage * 10)
            else -> "Unrated"
        }
        val description = if (hasRating) "User score" else "Unrated"
        Icon(
            painter = painterResource(id = starRes),
            contentDescription = description,
            tint = Orange600,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = score,
            style = MaterialTheme.typography.subtitle2,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 4.dp, end = 2.dp),
            color = Color.LightGray
        )
    }
}

@Preview
@Composable
private fun ShowMovieCard() {
    val movie = Movie(
        id = 1,
        title = "Out Little Land",
        releaseDate = "2022-10-10",
        voteAverage = 7.8,
        voteCount = 10
    )
    MovieCard(movie = movie)
}

@Preview
@Composable
private fun ShowUnratedMovieCard() {
    val movie = Movie(id = 1, title = "Out Little Land", releaseDate = "2022-10-10")
    MovieCard(movie = movie)
}
