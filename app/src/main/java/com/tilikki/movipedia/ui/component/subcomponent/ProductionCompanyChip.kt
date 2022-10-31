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
import com.tilikki.movipedia.model.ProductionCompany
import com.tilikki.movipedia.util.generateCountryFlagEmoji

@Composable
fun ProductionCompanyChip(
    productionCompany: ProductionCompany,
    backgroundColor: Color = Color.LightGray,
    shape: Shape = RoundedCornerShape(8.dp)
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .background(color = backgroundColor, shape = shape)
            .padding(horizontal = 4.dp)
    ) {
        val countryFlag = if (productionCompany.originCountryCode.isNotBlank()) {
            generateCountryFlagEmoji(productionCompany.originCountryCode)
        } else {
            ""
        }
        Text(
            text = "$countryFlag ${productionCompany.name}",
            modifier = Modifier
                .padding(4.dp),
            style = MaterialTheme.typography.caption
        )
    }
}

@Composable
fun ProductionCompanyChips(
    productionCompanies: List<ProductionCompany>,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.LightGray,
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
fun PreviewProductionCompanyChip() {
    ProductionCompanyChip(productionCompany = ProductionCompany(123, "Universal", "US", ""))
}

@Preview
@Composable
fun PreviewProductionCompanyChips() {
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

