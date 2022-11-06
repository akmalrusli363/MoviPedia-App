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
import com.tilikki.movipedia.model.ProductionCompany
import com.tilikki.movipedia.ui.component.generic.GenericChip
import com.tilikki.movipedia.ui.theme.getChipBackgroundColor
import com.tilikki.movipedia.util.generateCountryFlagEmoji

@Composable
fun ProductionCompanyChip(
    productionCompany: ProductionCompany,
    backgroundColor: Color = getChipBackgroundColor(),
    shape: Shape = RoundedCornerShape(8.dp)
) {
    val countryFlag = if (productionCompany.originCountryCode.isNotBlank()) {
        generateCountryFlagEmoji(productionCompany.originCountryCode)
    } else {
        ""
    }
    val productionCompanyName = "$countryFlag ${productionCompany.name}".trim()
    GenericChip(
        text = productionCompanyName,
        innerPadding = PaddingValues(4.dp),
        backgroundColor = backgroundColor,
        shape = shape,
    )
}

@Composable
fun ProductionCompanyChips(
    productionCompanies: List<ProductionCompany>,
    modifier: Modifier = Modifier,
    backgroundColor: Color = getChipBackgroundColor(),
    shape: Shape = RoundedCornerShape(8.dp)
) {
    LazyRow(modifier = modifier) {
        items(productionCompanies) { company ->
            ProductionCompanyChip(company, backgroundColor, shape)
        }
    }
}

@Preview
@Composable
private fun PreviewProductionCompanyChip() {
    ProductionCompanyChip(productionCompany = ProductionCompany(123, "Universal", "US", ""))
}

@Preview
@Composable
private fun PreviewProductionCompanyChips() {
    val productionCompanies = listOf(
        ProductionCompany(1, "Universal Studio", "US", ""),
        ProductionCompany(2, "Dragon Production", "cn", ""),
        ProductionCompany(13, "Konnichiwa Studio", "jp", ""),
        ProductionCompany(23, "Surya", "id", ""),
        ProductionCompany(23, "MARVEL", "us", ""),
        ProductionCompany(44, "Champion Brasil", "Br", ""),
    )
    ProductionCompanyChips(productionCompanies)
}

