package fr.insideapp.turnipoffkmm.android.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.pager.*
import fr.insideapp.turnipoffkmm.android.R
import fr.insideapp.turnipoffkmm.network.PictureSizes
import fr.insideapp.turnipoffkmm.android.ui.theme.Margin
import fr.insideapp.turnipoffkmm.model.search.MovieSearchResult

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreenTrending(navController: NavController, trendingViewModel: HomeScreenTrendingViewModel = viewModel(), configuration: Configuration) {
    Column(
        modifier = Modifier.height((configuration.screenHeightDp * 0.5).dp),
        verticalArrangement = Arrangement.spacedBy(Margin.normal)
    ) {
        val pagerState = rememberPagerState()

        HomeScreenTrendingList(
            navController = navController,
            pagerState = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            trendingViewModel = trendingViewModel
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
private fun HomeScreenTrendingList(navController: NavController, pagerState: PagerState, modifier: Modifier, trendingViewModel: HomeScreenTrendingViewModel) {
    val trendingMovies = remember { trendingViewModel.movieTrending }

    HorizontalPager(
        count = trendingMovies.count(),
        state = pagerState,
        modifier = modifier
    ) { page ->
        val posterPath = trendingMovies[page].posterPath

        Card(
            modifier = Modifier
                .clickable {
                    navController.navigate("movie/${trendingMovies[page].title}/${trendingMovies[page].id}")
                }
                .wrapContentWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            if(posterPath != null) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = PictureSizes.Poster.W185.buildURL(posterPath),
                        placeholder = painterResource(id = R.drawable.missing_picture)
                    ),
                    contentScale = ContentScale.FillHeight,
                    contentDescription = null,
                    modifier = Modifier.fillMaxHeight().wrapContentWidth()
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.missing_picture),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier.fillMaxHeight().wrapContentWidth()
                )
            }
        }
    }
}