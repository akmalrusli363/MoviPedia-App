package com.tilikki.movipedia.ui.component.subcomponent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Field with chip is a composable element for field label with chips (or other elements) placed below label.
 * If the list data don't available, the property field will return "none" beside field label.
 * Else, if data are available, the elements (chips) will be shown below field label.
 */
@Composable
fun <T> FieldWithChip(
    title: String,
    list: List<T>,
    modifier: Modifier,
    chips: @Composable (List<T>) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(modifier = Modifier.padding(vertical = 4.dp)) {
            Text(
                text = "$title:",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(end = 4.dp),
                fontWeight = FontWeight.Medium,
            )
            if (list.isEmpty()) {
                Text(
                    text = "Not available",
                    modifier = Modifier.padding(horizontal = 4.dp),
                    style = MaterialTheme.typography.body2,
                )
            }
        }
        chips(list)
    }
}