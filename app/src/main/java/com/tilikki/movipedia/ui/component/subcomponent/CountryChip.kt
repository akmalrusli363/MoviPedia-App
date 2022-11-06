package com.tilikki.movipedia.ui.component.subcomponent

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tilikki.movipedia.model.general.Country
import com.tilikki.movipedia.ui.component.generic.GenericChip
import com.tilikki.movipedia.ui.theme.getChipBackgroundColor
import com.tilikki.movipedia.util.generateCountryFlagEmoji

@Composable
fun CountryChip(
    country: Country,
    backgroundColor: Color = getChipBackgroundColor(),
    shape: Shape = RoundedCornerShape(8.dp)
) {
    val countryFlag = generateCountryFlagEmoji(country.countryCode)
    GenericChip(
        text = "$countryFlag ${country.name}".trim(),
        innerPadding = PaddingValues(4.dp),
        backgroundColor = backgroundColor,
        shape = shape,
    )
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

