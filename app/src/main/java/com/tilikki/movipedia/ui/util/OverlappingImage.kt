package com.tilikki.movipedia.ui.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tilikki.movipedia.R

@Composable
fun OverlappingImage(
    painter: Painter,
    contentDescription: String,
    modifier: Modifier,
    contentScale: ContentScale
) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier.drawWithCache {
            val gradient = Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color.Black),
                startY = size.height / 3,
                endY = size.height
            )
            onDrawWithContent {
                drawContent()
                drawRect(gradient, blendMode = BlendMode.Multiply)
            }
        },
        contentScale = contentScale
    )
}

@Composable
fun OverlappingAsyncImage(
    model: String,
    placeholder: Painter,
    contentDescription: String,
    modifier: Modifier,
    contentScale: ContentScale
) {
    AsyncImage(
        model = model,
        placeholder = placeholder,
        contentDescription = contentDescription,
        modifier = modifier.drawWithCache {
            val gradient = Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color.Black),
                startY = size.height / 4,
                endY = size.height
            )
            onDrawWithContent {
                drawContent()
                drawRect(gradient, blendMode = BlendMode.Multiply)
            }
        },
        contentScale = contentScale
    )
}

@Preview
@Composable
fun PreviewOverlappingImage() {
    OverlappingImage(
        painter = painterResource(id = R.drawable.sample_movie_backdrop),
        contentDescription = "Sample backdrop: black adam",
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .defaultMinSize(minHeight = 120.dp),
        contentScale = ContentScale.FillWidth
    )
}

@Preview
@Composable
fun PreviewOverlappingAsyncImage() {
    OverlappingAsyncImage(
        model = "https://via.placeholder.com/600x360.png?text=Backdrop+Example",
        placeholder = ColorPainter(Color.Gray),
        contentDescription = "Backdrop Example",
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 120.dp),
        contentScale = ContentScale.FillWidth
    )
}