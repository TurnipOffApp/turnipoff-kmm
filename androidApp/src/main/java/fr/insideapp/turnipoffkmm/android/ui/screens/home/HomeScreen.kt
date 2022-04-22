package fr.insideapp.turnipoffkmm.android.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
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
    val viewModel: HomeScreenViewModel = viewModel()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(Margin.medium)
    ) {
        HomeScreenTrending(
            navController = navController,
            viewModel = viewModel,
            configuration = configuration
        )

        Divider(
            modifier = Modifier.background(Color.Gray)
        )

        WorstActionMovies(navController)

        Divider(
            modifier = Modifier.background(Color.Gray)
        )

        Worst90Movies(navController)

        Divider(
            modifier = Modifier.background(Color.Gray)
        )

        Worst80Movies(navController)

        Divider(
            modifier = Modifier.background(Color.Gray)
        )

        WorstComedyMovies(navController)
    }
}

@Composable
fun HomeScreenTrending(navController: NavController, viewModel: HomeScreenViewModel, configuration: Configuration) {
    val trendingMovies = viewModel.movieTrending

    HomeScreenTrending(
        navController = navController,
        trendingMovies = trendingMovies,
        configuration = configuration
    )

    viewModel.getMovieTrendingList()
}

@Composable
fun WorstActionMovies(navController: NavController) {
    val viewModel: HomeScreenSectionViewModel = viewModel()
    val worstActionMovies = viewModel.worstActionMovies

    HomeScreenSection(navController, worstActionMovies) {
        viewModel.loadMore(it)
    }
}

@Composable
fun Worst90Movies(navController: NavController) {
    val viewModel: HomeScreenSectionViewModel = viewModel()
    val worst90sMovies = viewModel.worst90sMovies

    HomeScreenSection(navController, worst90sMovies) {
        viewModel.loadMore(it)
    }
}

@Composable
fun Worst80Movies(navController: NavController) {
    val viewModel: HomeScreenSectionViewModel = viewModel()
    val worst80sMovies = viewModel.worst80sMovies

    HomeScreenSection(navController, worst80sMovies) {
        viewModel.loadMore(it)
    }
}

@Composable
fun WorstComedyMovies(navController: NavController) {
    val viewModel: HomeScreenSectionViewModel = viewModel()
    val worstComedyMovies = viewModel.worstComedyMovies

    HomeScreenSection(navController, worstComedyMovies) {
        viewModel.loadMore(it)
    }
}