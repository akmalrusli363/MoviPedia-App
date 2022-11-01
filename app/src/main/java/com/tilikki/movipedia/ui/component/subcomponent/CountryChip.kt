package com.tilikki.movipedia.ui.component.subcomponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tilikki.movipedia.model.general.Country
import com.tilikki.movipedia.ui.theme.getChipBackgroundColor
import com.tilikki.movipedia.util.generateCountryFlagEmoji

@Composable
fun CountryChip(
    country: Country,
    backgroundColor: Color = getChipBackgroundColor(),
    shape: Shape = RoundedCornerShape(8.dp)
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .background(color = backgroundColor, shape = shape)
            .padding(horizontal = 4.dp)
    ) {
        val countryFlag = generateCountryFlagEmoji(country.countryCode)
        Text(
            text = "$countryFlag ${country.name}",
            modifier = Modifier
                .padding(4.dp),
            style = MaterialTheme.typography.caption
        )
    }
}

@Composable
fun CountryChips(
    countries: List<Country>,
    modifier: Modifier = Modifier,
    backgroundColor: Color = getChipBackgroundColor(),
    shape: Shape = RoundedCornerShape(8.dp)
) {
    LazyRow(modifier = modifier) {
        items(countries) { country ->
            CountryChip(country, backgroundColor, shape)
        }
    }
}

@Preview
@Composable
private fun PreviewCountryChip() {
    CountryChip(Country("Japan", "jp"))
}

@Preview
@Composable
private fun PreviewCountryChips() {
    val countries = listOf(
        Country("Argentina", "ar"),
        Country("China", "cn"),
        Country("Indonesia", "id"),
        Country("Japan", "jp"),
        Country("Russia", "ru"),
        Country("United States of America", "US"),
    )
    CountryChips(countries)
}

