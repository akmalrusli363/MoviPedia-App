package com.tilikki.movipedia.ui.component.generic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tilikki.movipedia.ui.theme.getChipBackgroundColor
import com.tilikki.movipedia.util.runIfNotNull

@Composable
fun GenericChip(
    text: String,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
    backgroundColor: Color = getChipBackgroundColor(),
    shape: Shape = RoundedCornerShape(8.dp),
    onClickAction: (() -> Unit)? = null,
    textStyle: TextStyle = MaterialTheme.typography.caption,
) {
    val padModifier = modifier
        .padding(horizontal = 4.dp)
        .clip(shape)
        .runIfNotNull(onClickAction) { onClick ->
            return@runIfNotNull this.clickable { onClick() }
        }
    Card(
        shape = shape,
        backgroundColor = backgroundColor,
        modifier = padModifier
    ) {
        Text(
            text = text,
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .padding(innerPadding),
            style = textStyle,
            textAlign = TextAlign.Center
        )
    }
}