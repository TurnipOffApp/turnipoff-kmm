package fr.insideapp.turnipoffkmm.android.ui.screens.movie

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import fr.insideapp.turnipoffkmm.android.R
import fr.insideapp.turnipoffkmm.android.ui.theme.Margin
import fr.insideapp.turnipoffkmm.model.movie.MovieCredits
import fr.insideapp.turnipoffkmm.network.PictureSizes

private typealias NavigateTo = (personName: String, personId: Long) -> Unit

@Composable
fun MovieScreen(navController: NavController, movieName: String, movieId: Long) {
    val scrollState = rememberScrollState()
    val viewModel: MovieScreenViewModel = viewModel(factory = MovieScreenViewModelFactory(movieId))

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = movieName
                    )
                },
                navigationIcon = if (navController.previousBackStackEntry != null) {
                    {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                } else {
                    null
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        content = {
            Column(
                modifier = Modifier.verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(Margin.medium)
            ) {
                MovieDetails(viewModel = viewModel)
                MovieCredits(
                    viewModel = viewModel,
                    navigateTo = { personName, personId ->
                        navController.navigate("person/${personName}/${personId}")
                    }
                )
            }
        }
    )

}

@Composable
private fun MovieDetails(viewModel: MovieScreenViewModel) {
    val movie = viewModel.movieDetails

    if(movie != null) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Margin.medium)
        ) {
            val backdropPath = movie.backdropPath
            val posterPath = movie.posterPath
            if(backdropPath != null || posterPath != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                ) {
                    if(backdropPath != null) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = PictureSizes.Backdrop.W700.buildURL(backdropPath),
                                placeholder = painterResource(id = R.drawable.missing_picture)
                            ),
                            contentScale = ContentScale.FillWidth,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .blur(radius = 16.dp)
                        )
                    }

                    if(posterPath != null) {
                        Card(
                            modifier = Modifier
                                .padding(Margin.normal)
                                .fillMaxHeight(),
                            elevation = Margin.medium,
                            shape = RoundedCornerShape(Margin.medium)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = PictureSizes.Poster.W500.buildURL(posterPath),
                                    placeholder = painterResource(id = R.drawable.missing_picture)
                                ),
                                contentScale = ContentScale.FillHeight,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxHeight()
                            )
                        }
                    }
                }

                Divider(
                    modifier = Modifier.background(Color.Gray)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Margin.normal)
            ) {
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .border(width = 1.dp, color = Color.Gray, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        fontWeight = FontWeight.Bold,
                        text = movie.voteAverage.toString(),
                        textAlign = TextAlign.Center
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1.0f)
                        .height(60.dp),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        fontWeight = FontWeight.Bold,
                        text = movie.title
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(Margin.medium)
                    ) {
                        Text(
                            text = "${movie.releaseDate?.year ?: "N/A"}"
                        )
                        Text(text = movie.productionCountries.joinToString(",") { it.name })
                    }
                }
            }


            if(movie.overview.isNotBlank()) {
                Divider(
                    modifier = Modifier.background(Color.Gray)
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Margin.normal),
                    text = movie.overview,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}

@Composable
private fun MovieCredits(viewModel: MovieScreenViewModel, navigateTo: NavigateTo = {_,_ -> }) {
    val movieCredits = viewModel.movieCredits

    if(movieCredits != null) {
        Column(
            modifier = Modifier.wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(Margin.medium)
        ) {
            Divider(
                modifier = Modifier.background(Color.Gray)
            )
            if(movieCredits.cast.isNotEmpty()) {
                CreditList(
                    title = "Cast",
                    credits = movieCredits.cast,
                    navigateTo = navigateTo
                )
            }
            if(movieCredits.crew.isNotEmpty()) {
                CreditList(
                    title = "Crew",
                    credits = movieCredits.crew,
                    navigateTo = navigateTo
                )
            }
        }
    }
}

@Composable
private fun CreditList(title: String, credits: List<MovieCredits.Credit>, navigateTo: NavigateTo = {_,_ -> }) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Margin.medium)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = Margin.normal),
            fontWeight = FontWeight.Bold,
            text = title
        )
        LazyRow(
            modifier = Modifier.wrapContentHeight(),
            contentPadding = PaddingValues(horizontal = Margin.normal),
            horizontalArrangement = Arrangement.spacedBy(Margin.normal)
        ) {
            items(credits) { credit ->
                CreditItem(
                    credit = credit,
                    navigateTo = navigateTo
                )
            }
        }
    }
}

@Composable
private fun CreditItem(credit: MovieCredits.Credit, navigateTo: NavigateTo = {_,_ -> }) {
    val creditPath = credit.profilePath
    Column(
        modifier = Modifier.width(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Margin.medium)
    ) {
        Card(
            elevation = Margin.medium,
            shape = RoundedCornerShape(Margin.medium),
            modifier = Modifier.clickable {
                navigateTo(credit.name, credit.id)
            }
        ) {
            if(creditPath != null) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = PictureSizes.Profile.W185.buildURL(creditPath),
                        placeholder = painterResource(id = R.drawable.missing_picture)
                    ),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 60.dp, height = 80.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.missing_picture),
                    contentScale = ContentScale.FillHeight,
                    contentDescription = null,
                    modifier = Modifier.size(width = 60.dp, height = 80.dp)
                )
            }

        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = credit.name,
                textAlign = TextAlign.Center,
                fontSize = 10.sp
            )
            Text(
                text = credit.subtitle,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                fontSize = 8.sp
            )
        }

    }
}