package com.tilikki.movipedia.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Yellow400 = Color(0xFFFFEE58)
val Orange600 = Color(0xFFFFB300)
val Orange700 = Color(0xFFF57C00)
val PurpleA400 = Color(0xFF651FFF)
val DarkBlack = Color(0xFF1A1A1A)
val DarkBlackAlt = Color(0xFF2A2A2A)
val DarkGray = Color(0xFF404040)
val WhiteAlt = Color(0xFFF0F0F0)

@Composable
fun getCardBackgroundColor(): Color =
    if (MaterialTheme.colors.isLight) WhiteAlt else DarkBlackAlt

@Composable
fun getChipBackgroundColor(): Color =
    if (MaterialTheme.colors.isLight) Color.LightGray else DarkGray

@Composable
fun getParagraphTextColor(): Color =
    if (MaterialTheme.colors.isLight) Color.DarkGray else Color.LightGray
