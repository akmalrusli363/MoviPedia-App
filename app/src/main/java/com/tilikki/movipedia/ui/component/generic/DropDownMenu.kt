package com.tilikki.movipedia.ui.component.generic

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.tilikki.movipedia.util.indexOfOrFirstIndex

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> DropDownMenu(
    label: String,
    listData: List<T>,
    modifier: Modifier = Modifier,
    displayText: (T) -> String = { it.toString() },
    onItemMenuChanged: (T) -> Unit = {},
    innerData: @Composable (T) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        val value = if (listData.isNotEmpty()) displayText(listData[selectedIndex]) else ""
        TextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            onDismissRequest = {
                expanded = false
            },
            expanded = expanded
        ) {
            listData.forEachIndexed { index, item ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                    onItemMenuChanged(item)
                }) {
                    innerData(item)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> DropDownMenu(
    label: String,
    listData: List<T>,
    defaultValue: T,
    modifier: Modifier = Modifier,
    displayText: (T) -> String = { it.toString() },
    onItemMenuChanged: (T) -> Unit = {},
    compareValue: ((source: T, defaultValue: T) -> Boolean)? = null,
    innerData: @Composable (T) -> Unit,
) {
    val initialSelectedIndex = if (compareValue != null) {
        listData.indexOfOrFirstIndex {
            compareValue(it, defaultValue)
        }
    } else {
        listData.indexOfOrFirstIndex(defaultValue)
    }
    var edited by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(initialSelectedIndex) }
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        val selIndex = if (!edited) initialSelectedIndex else selectedIndex
        val value = if (listData.isNotEmpty()) displayText(listData[selIndex]) else ""
        TextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            modifier = modifier,
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            onDismissRequest = {
                expanded = false
            },
            expanded = expanded
        ) {
            listData.forEachIndexed { index, item ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                    edited = true
                    onItemMenuChanged(item)
                }) {
                    innerData(item)
                }
            }
        }
    }
}