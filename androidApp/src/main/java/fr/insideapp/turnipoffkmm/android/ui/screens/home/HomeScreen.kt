package fr.insideapp.turnipoffkmm.android.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.insideapp.turnipoffkmm.android.ui.theme.Margin

@Composable
fun HomeScreen(navController: NavController) {
    val configuration = LocalConfiguration.current
    val scrollState = rememberScrollState()

    val sections = listOf(
        HomeScreenSectionDataHolder.Type.WorstAction,
        HomeScreenSectionDataHolder.Type.Worst90,
        HomeScreenSectionDataHolder.Type.Worst80,
        HomeScreenSectionDataHolder.Type.WorstComedy
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "TurnipOFF"
                    )
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        content = {
            Column(
                modifier = Modifier.verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(Margin.medium)
            ) {
                Trending(
                    navController = navController,
                    configuration = configuration
                )

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    sections.forEach {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) {
                            Column {
                                Divider(
                                    modifier = Modifier.background(Color.Gray)
                                )
                                Section(
                                    navController = navController,
                                    type = it,
                                    configuration = configuration
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun Trending(navController: NavController, configuration: Configuration) {
    HomeScreenTrending(
        navController = navController,
        configuration = configuration
    )
}

@Composable
fun Section(
    navController: NavController,
    type: HomeScreenSectionDataHolder.Type,
    configuration: Configuration
) {
    HomeScreenSection(navController, type = type, configuration = configuration)
}