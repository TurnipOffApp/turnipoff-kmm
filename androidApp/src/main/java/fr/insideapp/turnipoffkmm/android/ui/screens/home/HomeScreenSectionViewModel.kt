package fr.insideapp.turnipoffkmm.android.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.insideapp.turnipoffkmm.android.logic.Service
import fr.insideapp.turnipoffkmm.model.TheMovieDBMovieGenre
import fr.insideapp.turnipoffkmm.model.TheMovieDBResponse
import fr.insideapp.turnipoffkmm.model.search.MovieSearchResult
import fr.insideapp.turnipoffkmm.network.TheMovieDBClient
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class HomeScreenSectionDataHolder(
    val type: Type,
    val title: String = type.title,
    var page: Int = 0,
    val pageMax: Int = 1,
    val data: List<MovieSearchResult> = listOf()
) {
    enum class Type(val title: String) {
        WorstAction("Worst action movies"),
        Worst90("Worst 90's movies"),
        Worst80("Worst 80's movies"),
        WorstComedy("Worst comedy movies"),
    }
}

class HomeScreenSectionViewModel() : ViewModel() {
    private val dateFormat = SimpleDateFormat("yyyy-dd-MM")

    var worstActionMovies: HomeScreenSectionDataHolder by mutableStateOf(HomeScreenSectionDataHolder(type = HomeScreenSectionDataHolder.Type.WorstAction))
    var worst90sMovies: HomeScreenSectionDataHolder by mutableStateOf(HomeScreenSectionDataHolder(type = HomeScreenSectionDataHolder.Type.Worst90))
    var worst80sMovies: HomeScreenSectionDataHolder by mutableStateOf(HomeScreenSectionDataHolder(type = HomeScreenSectionDataHolder.Type.Worst80))
    var worstComedyMovies: HomeScreenSectionDataHolder by mutableStateOf(HomeScreenSectionDataHolder(type = HomeScreenSectionDataHolder.Type.WorstComedy))

    var errorMessage: String by mutableStateOf("")

    fun loadMore(type: HomeScreenSectionDataHolder.Type) {
        when(type) {
            HomeScreenSectionDataHolder.Type.WorstAction -> getWorstActionMovies()
            HomeScreenSectionDataHolder.Type.Worst90 -> getWorst90sMovies()
            HomeScreenSectionDataHolder.Type.Worst80 -> getWorst80sMovies()
            HomeScreenSectionDataHolder.Type.WorstComedy -> getWorstComedyMovies()
        }
    }

    private fun getWorstActionMovies() {
        viewModelScope.launch {
            if(worstActionMovies.page < worstActionMovies.pageMax) {
                val newPage = worstActionMovies.page + 1

                try {
                    val moviesResult = Service.getInstance().client.discover(
                        genres = mutableListOf(TheMovieDBMovieGenre.Action),
                        sortby = "vote_average.asc",
                        voteAverage = "1",
                        page = newPage
                    )

                    processResponse(
                        type = HomeScreenSectionDataHolder.Type.WorstAction,
                        moviesResult = moviesResult,
                        oldData = worstActionMovies.data
                    )

                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }
    }

    private fun getWorst90sMovies() {
        viewModelScope.launch {
            if(worst90sMovies.page < worst90sMovies.pageMax) {
                val newPage = worst90sMovies.page + 1

                try {
                    val moviesResult = Service.getInstance().client.discover(
                        sortby = "vote_average.asc",
                        voteAverage = "1",
                        page = newPage,
                        releaseAfter = "1990-01-01",
                        releaseBefore = "1999-12-31",
                    )

                    processResponse(
                        type = HomeScreenSectionDataHolder.Type.Worst90,
                        moviesResult = moviesResult,
                        oldData = worst90sMovies.data
                    )
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }
    }

    private fun getWorst80sMovies() {
        viewModelScope.launch {
            if(worst80sMovies.page < worst80sMovies.pageMax) {
                val newPage = worst80sMovies.page + 1

                try {
                    val moviesResult = Service.getInstance().client.discover(
                        sortby = "vote_average.asc",
                        voteAverage = "1",
                        page = newPage,
                        releaseAfter = "1980-01-01",
                        releaseBefore = "1989-12-31",
                    )

                    processResponse(
                        type = HomeScreenSectionDataHolder.Type.Worst80,
                        moviesResult = moviesResult,
                        oldData = worst80sMovies.data
                    )
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }
    }

    private fun getWorstComedyMovies() {
        viewModelScope.launch {
            if(worstComedyMovies.page < worstComedyMovies.pageMax) {
                val newPage = worstComedyMovies.page + 1

                try {
                    val moviesResult = Service.getInstance().client.discover(
                        genres = mutableListOf(TheMovieDBMovieGenre.Comedy),
                        sortby = "vote_average.asc",
                        voteAverage = "1",
                        page = newPage
                    )

                    processResponse(
                        type = HomeScreenSectionDataHolder.Type.WorstComedy,
                        moviesResult = moviesResult,
                        oldData = worstComedyMovies.data
                    )

                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }
    }

    private fun processResponse(type: HomeScreenSectionDataHolder.Type, moviesResult: TheMovieDBResponse<MovieSearchResult>?, oldData: List<MovieSearchResult>) {
        if(moviesResult != null) {
            val newHolder = HomeScreenSectionDataHolder(
                type = type,
                page = moviesResult.page,
                pageMax = moviesResult.totalPage,
                data = (oldData + moviesResult.results)
            )

            when(type) {
                HomeScreenSectionDataHolder.Type.WorstAction -> worstActionMovies = newHolder
                HomeScreenSectionDataHolder.Type.Worst90 -> worst90sMovies = newHolder
                HomeScreenSectionDataHolder.Type.Worst80 -> worst80sMovies = newHolder
                HomeScreenSectionDataHolder.Type.WorstComedy -> worstComedyMovies = newHolder
            }
        }
    }
}