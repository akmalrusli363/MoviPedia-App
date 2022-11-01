package com.tilikki.movipedia.ui.component.subcomponent

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tilikki.movipedia.R
import com.tilikki.movipedia.ui.theme.Orange600
import com.tilikki.movipedia.ui.util.DotSeparator
import java.util.*
import kotlin.math.floor
import kotlin.math.round

@Composable
fun RatingCard(rating: Double, users: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
//        Icon(
//            imageVector = Icons.Default.Star,
//            contentDescription = null,
//            tint = Orange600,
//            modifier = Modifier
//                .size(48.dp)
//                .padding(4.dp)
//        )
        StarRating(
            rating = rating / 2,
            modifier = Modifier.padding(4.dp),
            ratingType = RatingType.FIVE_STAR
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = String.format(Locale.ROOT, "%.1f of 10.0", rating),
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "($users users)",
                style = MaterialTheme.typography.caption
            )
        }
        DotSeparator(Modifier.padding(8.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val satisfactionRate = round(rating * 10).toInt()
            Text(
                text = "$satisfactionRate%",
                style = MaterialTheme.typography.h6
            )
            Text(
                text = "User Rating",
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
fun StarRating(
    rating: Double,
    modifier: Modifier = Modifier,
    ratingType: RatingType = RatingType.TEN_STAR
) {
    val ratingInteger: Int = getMaxRating(rating, ratingType.maxRating)
    val half = rating - ratingInteger
    Row(modifier) {
        for (star in 0 until ratingInteger) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_star_24),
                contentDescription = null,
                tint = Orange600,
                modifier = Modifier
                    .size(ratingType.starSize)
            )
        }
        if (ratingInteger <= ratingType.maxRating && half >= 0.5 && half < 1) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_star_half_24),
                contentDescription = null,
                tint = Orange600,
                modifier = Modifier
                    .size(ratingType.starSize)
            )
        }
        for (star in round(rating).toInt() until ratingType.maxRating) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_star_border_24),
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier
                    .size(ratingType.starSize)
            )
        }
    }
}

private fun getMaxRating(rating: Double, maxRating: Int): Int {
    return if (rating < maxRating) {
        floor(rating).toInt()
    } else maxRating
}

@Preview
@Composable
private fun PreviewStarRating4Star() {
    StarRating(rating = 4.2)
}

@Preview
@Composable
private fun PreviewStarRating9Star() {
    StarRating(rating = 9.0)
}

@Preview
@Composable
private fun PreviewStarRatingHalfStar() {
    StarRating(rating = 8.8)
}

@Preview
@Composable
private fun PreviewStarRatingEmptyStar() {
    StarRating(rating = 0.0)
}

@Preview
@Composable
private fun PreviewFiveStarRating4Star() {
    StarRating(rating = 4.2, ratingType = RatingType.FIVE_STAR)
}

@Preview
@Composable
private fun PreviewFiveStarRating9Star() {
    StarRating(rating = 9.0, ratingType = RatingType.FIVE_STAR)
}

@Preview
@Composable
private fun PreviewFiveStarRatingHalfStar() {
    StarRating(rating = 3.7, ratingType = RatingType.FIVE_STAR)
}

@Preview
@Composable
private fun PreviewFiveStarRating1Star() {
    StarRating(rating = 1.0, ratingType = RatingType.FIVE_STAR)
}

@Preview
@Composable
private fun PreviewFiveStarRatingEmptyStar() {
    StarRating(rating = 0.0, ratingType = RatingType.FIVE_STAR)
}

@Preview
@Composable
private fun PreviewRatingCardFiveStar() {
    RatingCard(rating = 5.5, users = 123, modifier = Modifier.padding(8.dp))
}

@Preview
@Composable
private fun PreviewRatingCardTenStar() {
    RatingCard(rating = 10.0, users = 123, modifier = Modifier.padding(8.dp))
}