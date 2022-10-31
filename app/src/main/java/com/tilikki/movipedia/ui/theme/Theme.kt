package com.tilikki.movipedia.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tilikki.movipedia.R
import com.tilikki.movipedia.ui.component.TonedBottomNavigationBar

private val DarkColorPalette = darkColors(
    primary = Yellow400,
    primaryVariant = Orange700,
    secondary = PurpleA400,
    background = DarkBlack
)

private val LightColorPalette = lightColors(
    primary = Orange600,
    primaryVariant = Orange700,
    secondary = PurpleA400

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MoviPediaTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
private fun PreviewDarkModeTheme() {
    PreviewElement(true)
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
private fun PreviewLightModeTheme() {
    PreviewElement(false)
}

@Composable
private fun PreviewElement(darkMode: Boolean) {
    MoviPediaTheme(darkTheme = darkMode) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Preview") }
                )
            },
            bottomBar = {
                TonedBottomNavigationBar() {
                    BottomNavigationItem(
                        selected = true,
                        label = { Text("hello") },
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_baseline_local_movies_24),
                                contentDescription = null
                            )
                        },
                        onClick = {}
                    )
                    BottomNavigationItem(
                        selected = false,
                        label = { Text("bye") },
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_baseline_upcoming_24),
                                contentDescription = null
                            )
                        },
                        onClick = {}
                    )
                }
            }
        ) {
            Column(modifier = Modifier
                .padding(it)
                .padding(16.dp)) {
                CircularProgressIndicator()
                Text(text = "title", style = MaterialTheme.typography.h4)
                Checkbox(checked = true, onCheckedChange = {})
                Text(text = "Agree")
                Slider(value = 0.1f, onValueChange = {})
                TextField(value = "hint", onValueChange = {})
                Button(onClick = { }) {
                    Text(text = "I agree")
                }
            }
        }
    }
}