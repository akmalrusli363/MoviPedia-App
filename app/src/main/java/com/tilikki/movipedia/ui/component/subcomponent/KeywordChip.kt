package com.tilikki.movipedia.ui.component.subcomponent

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tilikki.movipedia.model.Keyword
import com.tilikki.movipedia.ui.component.generic.GenericChip
import com.tilikki.movipedia.ui.theme.getChipBackgroundColor

@Composable
fun KeywordChip(
    keyword: Keyword,
    backgroundColor: Color = getChipBackgroundColor(),
    shape: Shape = RoundedCornerShape(8.dp)
) {
    GenericChip(
        text = keyword.name,
        innerPadding = PaddingValues(4.dp),
        backgroundColor = backgroundColor,
        shape = shape,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun KeywordChips(
    keywords: List<Keyword>,
    modifier: Modifier = Modifier,
    backgroundColor: Color = getChipBackgroundColor(),
    shape: Shape = RoundedCornerShape(8.dp)
) {
    FlowRow(modifier = modifier) {
        keywords.forEach { keyword ->
            KeywordChip(keyword = keyword, backgroundColor, shape)
        }
    }
}

@Preview
@Composable
private fun PreviewKeywordChip() {
    KeywordChip(Keyword(123, "hello"))
}

@Preview
@Composable
private fun PreviewKeywordChips() {
    val keywords = listOf(
        Keyword(123, "hello"),
        Keyword(143, "gate"),
        Keyword(173, "love is all about killin myself"),
        Keyword(233, "cherry bomb"),
        Keyword(553, "lemao"),
    )
    KeywordChips(keywords, modifier = Modifier.padding(4.dp))
}

@Preview
@Composable
private fun PreviewLongKeywordChips() {
    val keywords = listOf(
        Keyword(123, "hello I am a fish"),
        Keyword(143, "stairway to the heaven"),
        Keyword(173, "dealing with crazy neighbourhood"),
        Keyword(209, "spiderman"),
        Keyword(233, "spiderman feat tom holland"),
        Keyword(333, "underworld not the nether"),
        Keyword(553, "hell on the highway"),
    )
    KeywordChips(keywords, modifier = Modifier.padding(4.dp))
}

