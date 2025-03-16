package com.tilikki.movipedia.ui.util

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.primarySurface
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
    val bottomBarPadding = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()
    val backgroundColor = if (isLight) WhiteAlt else MaterialTheme.colors.primarySurface
    BottomAppBar(
        backgroundColor = backgroundColor,
        contentPadding = PaddingValues(bottom = bottomBarPadding),
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