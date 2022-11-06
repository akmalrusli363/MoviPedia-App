package com.tilikki.movipedia.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tilikki.movipedia.model.Genre
import com.tilikki.movipedia.ui.component.subcomponent.GenreChip
import com.tilikki.movipedia.ui.theme.getChipBackgroundColor

@Composable
fun GenrePicker(
    genres: List<Genre>,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    onGenreCardItemClicked: (Int) -> Unit = { }
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = modifier) {
        items(genres) { genre ->
            GenreChip(
                genre = genre,
                backgroundColor = getChipBackgroundColor(),
                shape = shape,
                onClickAction = onGenreCardItemClicked,
                modifier = Modifier.padding(8.dp),
                innerPadding = PaddingValues(8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewGenrePicker() {
    Column(modifier = Modifier.fillMaxWidth()) {
        GenrePicker(
            genres = listOf(
                Genre(1, "lmao"),
                Genre(2, "adventure"),
                Genre(3, "romance"),
                Genre(4, "k-drama"),
                Genre(5, "anime"),
                Genre(6, "family friendly"),
                Genre(7, "bizarre"),
                Genre(8, "horror"),
            ),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}
