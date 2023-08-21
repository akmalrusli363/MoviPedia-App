package com.tilikki.movipedia.view.appinfo

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.tilikki.movipedia.R
import com.tilikki.movipedia.model.general.Country
import com.tilikki.movipedia.model.general.Language
import com.tilikki.movipedia.ui.component.generic.DropDownMenu
import com.tilikki.movipedia.ui.theme.MoviPediaTheme
import com.tilikki.movipedia.view.ThemeEngineViewModel
import com.tilikki.movipedia.view.ThemeEngineViewModelFactory
import com.tilikki.movipedia.view.navigation.NavigationBackButton

@Composable
fun AppInfoScreen(
    viewModel: AppInfoViewModel = viewModel(
        factory = AppInfoViewModelFactory(LocalContext.current)
    ),
    themeViewModel: ThemeEngineViewModel = viewModel(
        factory = ThemeEngineViewModelFactory(LocalContext.current)
    ),
    navController: NavHostController? = null
) {
    val countryList = remember { viewModel.countryList }
    val languageList = remember { viewModel.languageList }
    val defaultSystemDarkMode = isSystemInDarkTheme()
    val darkMode = themeViewModel.darkMode.observeAsState(initial = defaultSystemDarkMode)
    val configData = AppInfoConfigData(
        countryList, languageList, viewModel.getTmdbRegion(), viewModel.getTmdbLanguage()
    )
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchConfig()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Settings") },
                navigationIcon = { NavigationBackButton(navController = navController) }
            )
        }
    ) {
        AppInfoContent(
            darkMode = darkMode,
            configData = configData,
            modifier = Modifier.padding(it),
            onChangeConfigDataAction = AppInfoConfigActionsImpl(viewModel, themeViewModel)
        )
    }
}

@Composable
private fun AppInfoScreen(
    darkMode: MutableState<Boolean>,
    configData: AppInfoConfigData,
    onChangeConfigDataAction: AppInfoConfigActions = MockAppInfoConfigActionsImpl(),
    navController: NavHostController? = null
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Settings") },
                navigationIcon = { NavigationBackButton(navController = navController) }
            )
        }
    ) {
        AppInfoContent(
            darkMode = darkMode,
            configData = configData,
            modifier = Modifier.padding(it),
            onChangeConfigDataAction = onChangeConfigDataAction,
        )
    }
}

@Composable
private fun AppInfoContent(
    darkMode: State<Boolean>,
    configData: AppInfoConfigData,
    modifier: Modifier = Modifier,
    onChangeConfigDataAction: AppInfoConfigActions = MockAppInfoConfigActionsImpl()
) {
    Surface(color = MaterialTheme.colors.background) {
        Column(modifier = modifier.padding(8.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Text("Theme")
                IconToggleButton(checked = darkMode.value, onCheckedChange = {
                    onChangeConfigDataAction.onChangeAppTheme(it)
                }) {
                    if (darkMode.value) {
                        Icon(
                            painter = painterResource(R.drawable.ic_baseline_dark_mode_24),
                            contentDescription = "Dark theme"
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.ic_baseline_light_mode_24),
                            contentDescription = "Light theme"
                        )
                    }
                }
            }
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text("Movie listing region", modifier = Modifier.padding(horizontal = 8.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    DropDownMenu(
                        label = "Region",
                        modifier = Modifier.fillMaxWidth(),
                        listData = configData.countryList,
                        defaultValue = configData.defaultCountry,
                        displayText = { it.name },
                        onItemMenuChanged = { onChangeConfigDataAction.onChangeTmdbRegion(it) }
                    ) { country ->
                        Text(text = "${country.getCountryFlagEmoji()} ${country.name}")
                    }
                }
            }
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text("Language", modifier = Modifier.padding(horizontal = 8.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    DropDownMenu(
                        label = "Language",
                        modifier = Modifier.fillMaxWidth(),
                        listData = configData.languageList,
                        defaultValue = configData.defaultLanguage,
                        displayText = { it.name },
                        compareValue = { source, defaultValue ->
                            source.languageCode == defaultValue.languageCode
                        },
                        onItemMenuChanged = { onChangeConfigDataAction.onChangeTmdbLanguage(it) }
                    ) { language ->
                        Text(text = language.name)
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "MoviPadia App, powered by TMDB API",
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "by @akmalrusli363", fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                val creditInfoText = buildAnnotatedString {
                    append("This product ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("uses the TMDB API")
                    }
                    append("\nbut is ")
                    withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append("not endorsed or certified")
                    }
                    append(" by TMDB.")
                }
                Text(
                    text = creditInfoText,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(horizontal = 4.dp, vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAppInfoScreen() {
    val systemTheme = isSystemInDarkTheme()
    val isDark = remember(Boolean) { mutableStateOf(systemTheme) }
    val countriesList = listOf(
        Country("Afghanistan", "AF"),
        Country("Australia", "AU"),
        Country("Brazil", "BR"),
        Country("China", "CN"),
        Country("India", "IN"),
        Country("Indonesia", "ID"),
        Country("Japan", "JP"),
        Country("United States", "US"),
    )
    val languagesList = listOf(
        Language(
            languageCode = "de",
            name = "German (Deutsch)",
        ),
        Language(
            languageCode = "en",
            name = "English",
        ),
        Language(
            languageCode = "es",
            name = "Spanish (Español)",
        ),
        Language(
            languageCode = "ar",
            name = "Arabic (العربية)",
        ),
        Language(
            languageCode = "zh",
            name = "Mandarin (普通话)"
        ),
        Language(
            languageCode = "ru",
            name = "Russian (Pусский)"
        ),
        Language(
            languageCode = "pl",
            name = "Polish (Polski)"
        ),
    )
    val configData = AppInfoConfigData(
        countryList = countriesList,
        languageList = languagesList
    )

    MoviPediaTheme(darkTheme = isDark.value) {
        Surface(color = MaterialTheme.colors.background) {
            AppInfoScreen(
                darkMode = isDark,
                configData = configData,
                navController = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAppInfoScreenDarkTheme() {
    val darkTheme = true
    val isDark = remember(Boolean) { mutableStateOf(darkTheme) }
    val countriesList = listOf(
        Country("Afghanistan", "AF"),
        Country("Australia", "AU"),
        Country("Brazil", "BR"),
        Country("China", "CN"),
        Country("India", "IN"),
        Country("Indonesia", "ID"),
        Country("Japan", "JP"),
        Country("United States", "US"),
    )
    val languagesList = listOf(
        Language(
            languageCode = "de",
            name = "German (Deutsch)",
        ),
        Language(
            languageCode = "en",
            name = "English",
        ),
        Language(
            languageCode = "es",
            name = "Spanish (Español)",
        ),
        Language(
            languageCode = "ar",
            name = "Arabic (العربية)",
        ),
        Language(
            languageCode = "zh",
            name = "Mandarin (普通话)"
        ),
        Language(
            languageCode = "ru",
            name = "Russian (Pусский)"
        ),
        Language(
            languageCode = "pl",
            name = "Polish (Polski)"
        ),
    )
    val configData = AppInfoConfigData(
        countryList = countriesList,
        languageList = languagesList,
        defaultCountry = Country.fromCountryCode("IN"),
        defaultLanguage = Language.fromLanguageCode("zh"),
    )

    MoviPediaTheme(darkTheme = isDark.value) {
        Surface(color = MaterialTheme.colors.background) {
            AppInfoScreen(
                darkMode = isDark,
                configData = configData,
                navController = null,
            )
        }
    }
}