package fr.insideapp.turnipoffkmm.android.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import fr.insideapp.turnipoffkmm.android.R
import fr.insideapp.turnipoffkmm.android.ui.theme.Margin
import fr.insideapp.turnipoffkmm.android.ui.utils.OnEndReached
import fr.insideapp.turnipoffkmm.network.PictureSizes

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreenSection(navController: NavController, type: HomeScreenSectionDataHolder.Type, viewModel: HomeScreenSectionViewModel = viewModel(), configuration: Configuration) {
    val listState = rememberLazyListState()
    val moviesState by viewModel.getHolder(type).collectAsState()

    Column(
        modifier = Modifier
            .height((configuration.screenHeightDp * 0.25).dp)
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(Margin.medium)
    ) {
        Text(
            text = moviesState.title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Margin.normal)
        )
        LazyRow(
            modifier = Modifier.wrapContentHeight(),
            contentPadding = PaddingValues(horizontal = Margin.normal),
            horizontalArrangement = Arrangement.spacedBy(Margin.normal),
            state = listState
        ) {
            items(moviesState.data) { item ->
                val posterPath = item.posterPath

                Card(
                    modifier = Modifier
                        .wrapContentWidth()
                        .clickable {
                            navController.navigate("movie/${item.title}/${item.id}")
                        }
                        .padding(8.dp),
                    elevation = 8.dp,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    if(posterPath != null) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = PictureSizes.Poster.W185.buildURL(posterPath),
                                placeholder = painterResource(id = R.drawable.missing_picture)
                            ),
                            contentScale = ContentScale.Crop,
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
    }

    listState.OnEndReached {
        viewModel.loadMore(moviesState.type)
    }
}
