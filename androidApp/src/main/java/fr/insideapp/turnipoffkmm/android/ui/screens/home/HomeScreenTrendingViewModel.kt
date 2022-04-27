package fr.insideapp.turnipoffkmm.android.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.insideapp.turnipoffkmm.android.logic.Service
import fr.insideapp.turnipoffkmm.model.TheMovieDBTimeWindow
import fr.insideapp.turnipoffkmm.model.TheMovieDBMediaType
import fr.insideapp.turnipoffkmm.model.search.MovieSearchResult
import fr.insideapp.turnipoffkmm.network.TheMovieDBClient
import kotlinx.coroutines.launch

class HomeScreenTrendingViewModel : ViewModel() {
    var movieTrending = mutableStateListOf<MovieSearchResult>()

    var errorMessage: String by mutableStateOf("")

    init {
        getMovieTrendingList()
    }

    fun getMovieTrendingList() {
        viewModelScope.launch {
            try {
                val newMovies = Service.getInstance().client.discover(
                    page = 1
                ).results

                movieTrending.addAll(newMovies)
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}