package fr.insideapp.turnipoffkmm.android.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.*
import fr.insideapp.turnipoffkmm.network.PictureSizes
import fr.insideapp.turnipoffkmm.android.ui.theme.Margin
import fr.insideapp.turnipoffkmm.model.search.MovieSearchResult

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreenTrending(navController: NavController, trendingMovies: List<MovieSearchResult>, configuration: Configuration) {
    Column(
        modifier = Modifier
            .height((configuration.screenHeightDp * 0.3).dp),
        verticalArrangement = Arrangement.spacedBy(Margin.normal)
    ) {
        val pagerState = rememberPagerState()

        HomeScreenTrendingList(
            navController = navController,
            pagerState = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            listTrending = trendingMovies
        )

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun HomeScreenTrendingList(navController: NavController, pagerState: PagerState, modifier: Modifier, listTrending: List<MovieSearchResult>) {
    HorizontalPager(
        count = listTrending.count(),
        state = pagerState,
        modifier = modifier
    ) { page ->
        val posterPath = listTrending[page].posterPath

        if(posterPath != null) {
            Image(
                painter = rememberAsyncImagePainter(PictureSizes.Poster.W342.buildURL(posterPath)),
                contentDescription = null,
                modifier = Modifier.clickable {
                    navController.navigate("movie/${listTrending[page].id}")
                }
            )
        }
    }
}