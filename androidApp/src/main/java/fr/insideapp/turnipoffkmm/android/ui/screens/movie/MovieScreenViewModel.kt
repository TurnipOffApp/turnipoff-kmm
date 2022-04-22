package fr.insideapp.turnipoffkmm.android.ui.screens.movie

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.insideapp.turnipoffkmm.android.logic.Service
import fr.insideapp.turnipoffkmm.model.movie.Movie
import fr.insideapp.turnipoffkmm.model.movie.MovieCredits
import fr.insideapp.turnipoffkmm.network.TheMovieDBClient
import kotlinx.coroutines.launch

class MovieScreenViewModel(val movieId: Long): ViewModel() {
    var movieDetails: Movie? by mutableStateOf(null)
    var movieCredits: MovieCredits? by mutableStateOf(null)

    init {
        refreshMovie()
        refreshMovieCredits()
    }

    fun refreshMovie() {
        viewModelScope.launch {
            val movieResult = Service.getInstance().client.getMovie(
                movieId = movieId
            )

            movieDetails = movieResult
        }
    }

    fun refreshMovieCredits() {
        viewModelScope.launch {
            val movieCreditsResult = Service.getInstance().client.getMovieCredits(
                movieId = movieId
            )

            movieCredits = movieCreditsResult
        }
    }
}

class MovieScreenViewModelFactory constructor(private val movieId: Long): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(MovieScreenViewModel::class.java)) {
            MovieScreenViewModel(movieId) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}