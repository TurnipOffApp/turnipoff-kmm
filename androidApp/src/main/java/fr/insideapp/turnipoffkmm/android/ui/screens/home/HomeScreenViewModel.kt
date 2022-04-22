package fr.insideapp.turnipoffkmm.android.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.insideapp.turnipoffkmm.model.TheMovieDBTimeWindow
import fr.insideapp.turnipoffkmm.model.TheMovieDBMediaType
import fr.insideapp.turnipoffkmm.model.search.MovieSearchResult
import fr.insideapp.turnipoffkmm.network.TheMovieDBClient
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {
    var movieTrending: List<MovieSearchResult> by mutableStateOf(listOf())

    var errorMessage: String by mutableStateOf("")

    fun getMovieTrendingList() {
        viewModelScope.launch {
            try {
                val test =  TheMovieDBClient.trending(
                    mediaType = TheMovieDBMediaType.Movie,
                    timeWindow = TheMovieDBTimeWindow.Week
                ).results

                movieTrending = test
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}