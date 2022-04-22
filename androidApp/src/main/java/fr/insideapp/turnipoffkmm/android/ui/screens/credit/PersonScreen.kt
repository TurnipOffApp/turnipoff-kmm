package fr.insideapp.turnipoffkmm.android.ui.screens.credit

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import fr.insideapp.turnipoffkmm.android.ui.theme.Margin
import fr.insideapp.turnipoffkmm.android.R
import fr.insideapp.turnipoffkmm.network.PictureSizes

@Composable
fun PersonScreen(navController: NavController, personId: Long) {
    val scrollState = rememberScrollState()
    val viewModel: PersonScreenViewModel = viewModel(factory = PersonScreenViewModelFactory(personId))

    Column(
        modifier = Modifier.verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(Margin.medium)
    ) {
        PersonDetails(viewModel = viewModel)
    }
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
                            elevation = Margin.medium,
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
                        text = person.popularity.toString(),
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
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(Margin.medium)
                    ) {
                        val birthday = person.birthDate

                        if(birthday != null) {
                            Text(text = person.birthDate.toString())
                            Text(text = " - ")
                        }
                        Text(text = person.department)
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
            }
        }
    }
}