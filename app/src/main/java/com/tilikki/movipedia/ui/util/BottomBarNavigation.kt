package com.tilikki.movipedia.ui.util

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import com.tilikki.movipedia.ui.theme.Orange700
import com.tilikki.movipedia.ui.theme.WhiteAlt

@Composable
fun TonedBottomNavigationBar(
    modifier: Modifier = Modifier,
    elevation: Dp = BottomNavigationDefaults.Elevation,
    content: @Composable RowScope.() -> Unit
) {
    val isLight = MaterialTheme.colors.isLight
    BottomNavigation(
        backgroundColor = if (isLight) WhiteAlt else MaterialTheme.colors.primarySurface,
        contentColor = if (isLight) Orange700 else MaterialTheme.colors.onBackground,
        modifier = modifier,
        elevation = elevation,
        content = content,
    )
}

@Composable
fun BottomBarNavigationIcon(
    painter: Painter, contentDescription: String?,
    modifier: Modifier = Modifier,
    color: Color,
    onClick: () -> Unit
) {
    val animatedColor by animateColorAsState(
        targetValue = color,
        animationSpec = TweenSpec(
            durationMillis = 2000,
            easing = FastOutSlowInEasing
        )
    )

    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            tint = animatedColor,
        )
    }
}