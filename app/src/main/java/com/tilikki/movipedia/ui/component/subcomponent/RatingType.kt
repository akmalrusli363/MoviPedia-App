package com.tilikki.movipedia.ui.component.subcomponent

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class RatingType(val maxRating: Int, val starSize: Dp) {
    FIVE_STAR(5, 24.dp),
    TEN_STAR(10, 12.dp),
}
