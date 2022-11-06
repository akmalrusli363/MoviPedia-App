package com.tilikki.movipedia.ui.component.subcomponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tilikki.movipedia.model.Genre
import com.tilikki.movipedia.ui.theme.getChipBackgroundColor
import com.tilikki.movipedia.util.runIfNotNull

@Composable
fun GenreChip(
    genre: Genre,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(),
    backgroundColor: Color = getChipBackgroundColor(),
    shape: Shape = RoundedCornerShape(8.dp),
    onClickAction: ((Int) -> Unit)? = null,
) {
    val padModifier = modifier
        .padding(horizontal = 4.dp)
        .clip(shape)
        .runIfNotNull(onClickAction) { onClick ->
            return@runIfNotNull this.clickable { onClick(genre.id) }
        }
    Card(
        shape = shape,
        backgroundColor = backgroundColor,
        modifier = padModifier
    ) {
        Text(
            text = genre.name,
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .padding(innerPadding)
                .padding(2.dp),
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun GenreChips(
    genres: List<Genre>,
    modifier: Modifier = Modifier,
    backgroundColor: Color = getChipBackgroundColor(),
    shape: Shape = RoundedCornerShape(8.dp),
    onClickAction: ((Int) -> Unit)? = null,
) {
    LazyRow(modifier = modifier) {
        items(genres) { genre ->
            GenreChip(
                genre = genre,
                backgroundColor = backgroundColor,
                shape = shape,
                onClickAction = onClickAction
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
