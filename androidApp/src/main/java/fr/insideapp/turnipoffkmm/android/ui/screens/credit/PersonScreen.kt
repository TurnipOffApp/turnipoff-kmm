package fr.insideapp.turnipoffkmm.android.ui.screens.credit

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
import fr.insideapp.turnipoffkmm.android.ui.theme.Margin
import fr.insideapp.turnipoffkmm.android.R
import fr.insideapp.turnipoffkmm.model.movie.MovieCredits
import fr.insideapp.turnipoffkmm.network.PictureSizes

private typealias NavigateTo = (movieName: String, movieId: Long) -> Unit

@Composable
fun PersonScreen(navController: NavController, personName: String, personId: Long) {
    val scrollState = rememberScrollState()
    val viewModel: PersonScreenViewModel = viewModel(factory = PersonScreenViewModelFactory(personId))

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = personName
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
                PersonDetails(viewModel = viewModel)
                PersonCredits(
                    viewModel = viewModel,
                    navigateTo = { movieName, movieId ->
                        navController.navigate("movie/${movieName}/${movieId}")
                    }
                )
            }
        }
    )
}

@Composable
private fun PersonDetails(viewModel: PersonScreenViewModel) {
    val person = viewModel.personDetails

    if(person != null) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Margin.medium)
        ) {
            val profilePath = person.profilePath
            if(profilePath != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                ) {
                    if(profilePath != null) {
                        Card(
                            modifier = Modifier
                                .padding(Margin.normal)
                                .fillMaxHeight(),
                            shape = RoundedCornerShape(Margin.medium)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = PictureSizes.Profile.W185.buildURL(profilePath),
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
                        text = "%.1f".format(viewModel.personAverage),
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
                        text = person.name
                    )

                    val birthday = person.birthDate

                    if(birthday != null) {
                        Text(text = birthday)
                    }

                    val placeOfBirth = person.placeOfBirth

                    if(placeOfBirth != null) {
                        Text(text = placeOfBirth)
                    }
                }
            }

            Divider(
                modifier = Modifier.background(Color.Gray)
            )

            if(!person.biography.isNullOrBlank()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Margin.normal),
                    text = person.biography ?: "",
                    textAlign = TextAlign.Justify
                )
            } else {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Margin.normal),
                    text = "No biography.",
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}

@Composable
private fun PersonCredits(viewModel: PersonScreenViewModel, navigateTo: NavigateTo = { _, _ -> }) {
    val movieCredits = viewModel.personCredits

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
private fun CreditList(title: String, credits: List<MovieCredits.Credit>, navigateTo: NavigateTo = { _, _ -> }) {
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
private fun CreditItem(credit: MovieCredits.Credit, navigateTo: NavigateTo = { _, _ -> }) {
    val creditPath = credit.posterPath

    Column(
        modifier = Modifier.width(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Margin.medium)
    ) {
        Card(
            shape = RoundedCornerShape(Margin.medium),
            modifier = Modifier.clickable {
                navigateTo(credit.title, credit.id)
            }
        ) {
            if(creditPath != null) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = PictureSizes.Poster.W185.buildURL(creditPath),
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
                text = credit.title,
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