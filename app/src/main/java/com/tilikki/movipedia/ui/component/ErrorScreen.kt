package com.tilikki.movipedia.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tilikki.movipedia.R

@Composable
fun ErrorScreen(
    painter: Painter,
    painterContentDescription: String = "",
    errorMessage: String,
    error: Throwable? = null,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Icon(
                painter = painter,
                contentDescription = painterContentDescription,
                tint = Color.Gray,
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize()
                    .defaultMinSize(minWidth = 64.dp, minHeight = 64.dp)
            )
            Text(text = errorMessage, modifier = Modifier.padding(vertical = 4.dp))
            error?.let { err ->
                Text(
                    text = "Detail: ${err.message}",
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun RetriableErrorScreen(
    painter: Painter,
    painterContentDescription: String = "",
    errorMessage: String,
    error: Throwable? = null,
    modifier: Modifier = Modifier,
    onRetryAction: () -> Unit = {},
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Icon(
                painter = painter,
                contentDescription = painterContentDescription,
                tint = Color.Gray,
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize()
                    .defaultMinSize(minWidth = 64.dp, minHeight = 64.dp)
            )
            Text(text = errorMessage, modifier = Modifier.padding(vertical = 4.dp))
            error?.let { err ->
                Text(
                    text = "Detail: ${err.message}",
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
            Button(onClick = onRetryAction) {
                Text(text = "Retry")
            }
        }
    }
}

@Composable
fun MovieNotFoundScreen(
    error: Exception?,
    modifier: Modifier = Modifier
) {
    ErrorScreen(
        painter = painterResource(id = R.drawable.ic_baseline_local_movies_24),
        errorMessage = "Movie not found!",
        painterContentDescription = "Movie",
        error = error,
        modifier = modifier,
    )
}

@Composable
fun MovieFetchErrorScreen(
    error: Exception?,
    modifier: Modifier = Modifier,
    onRetryAction: () -> Unit = {}
) {
    RetriableErrorScreen(
        painter = painterResource(id = R.drawable.ic_baseline_cloud_off_24),
        errorMessage = "Error while fetch movie!",
        painterContentDescription = "Movie",
        error = error,
        modifier = modifier,
        onRetryAction = onRetryAction,
    )
}

@Preview
@Composable
private fun PreviewMovieNotFoundScreen() {
    MovieNotFoundScreen(error = null)
}

@Preview
@Composable
private fun PreviewMovieFetchErrorScreen() {
    MovieFetchErrorScreen(error = null)
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewMovieNotFoundScreenDarkTheme() {
    MovieNotFoundScreen(error = null)
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewMovieFetchErrorScreenDarkTheme() {
    MovieFetchErrorScreen(error = null)
}

