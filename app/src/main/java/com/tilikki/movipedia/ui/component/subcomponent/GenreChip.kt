package com.tilikki.movipedia.ui.component.subcomponent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tilikki.movipedia.model.Genre
import com.tilikki.movipedia.ui.component.generic.GenericChip
import com.tilikki.movipedia.ui.theme.getChipBackgroundColor

@Composable
fun GenreChip(
    genre: Genre,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(2.dp),
    backgroundColor: Color = getChipBackgroundColor(),
    shape: Shape = RoundedCornerShape(8.dp),
    onClickAction: ((Int) -> Unit)? = null,
) {
    GenericChip(
        text = genre.name,
        innerPadding = innerPadding,
        textStyle = MaterialTheme.typography.body2,
        backgroundColor = backgroundColor,
        shape = shape,
        modifier = modifier,
        onClickAction = if (onClickAction != null) {
            { onClickAction(genre.id) }
        } else null
    )
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
