package com.tilikki.movipedia.ui.component.subcomponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tilikki.movipedia.model.Genre

@Composable
fun GenreChips(
    genres: List<Genre>,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.LightGray,
    shape: Shape = RoundedCornerShape(8.dp)
) {
    LazyRow(modifier = modifier) {
        items(genres) { genre ->
            GenreChip(genre, backgroundColor, shape)
        }
    }
}

@Composable
fun GenreChip(
    genre: Genre,
    backgroundColor: Color = Color.LightGray,
    shape: Shape = RoundedCornerShape(8.dp)
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .background(color = backgroundColor, shape = shape)
            .padding(horizontal = 4.dp)
    ) {
        Text(
            text = genre.name,
            modifier = Modifier
                .padding(2.dp),
            style = MaterialTheme.typography.caption
        )
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
