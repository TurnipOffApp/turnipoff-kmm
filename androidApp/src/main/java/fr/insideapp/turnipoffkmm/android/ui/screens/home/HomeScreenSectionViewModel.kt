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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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
    private val worstActionMovies = MutableStateFlow(HomeScreenSectionDataHolder(type = HomeScreenSectionDataHolder.Type.WorstAction))
    private val worst90sMovies = MutableStateFlow(HomeScreenSectionDataHolder(type = HomeScreenSectionDataHolder.Type.Worst90))
    private val worst80sMovies = MutableStateFlow(HomeScreenSectionDataHolder(type = HomeScreenSectionDataHolder.Type.Worst80))
    private val worstComedyMovies = MutableStateFlow(HomeScreenSectionDataHolder(type = HomeScreenSectionDataHolder.Type.WorstComedy))

    var errorMessage: String by mutableStateOf("")

    fun getHolder(type: HomeScreenSectionDataHolder.Type): StateFlow<HomeScreenSectionDataHolder> {
        return when(type) {
            HomeScreenSectionDataHolder.Type.WorstAction -> worstActionMovies
            HomeScreenSectionDataHolder.Type.Worst90 -> worst90sMovies
            HomeScreenSectionDataHolder.Type.Worst80 -> worst80sMovies
            HomeScreenSectionDataHolder.Type.WorstComedy -> worstComedyMovies
        }
    }

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
            val holder = worstActionMovies.value

            if(holder.page < holder.pageMax) {
                val newPage = holder.page + 1

                try {
                    val moviesResult = Service.getInstance().client.discover(
                        genres = mutableListOf(TheMovieDBMovieGenre.Action),
                        page = newPage
                    )

                    processResponse(
                        type = HomeScreenSectionDataHolder.Type.WorstAction,
                        moviesResult = moviesResult,
                        oldData = holder.data
                    )

                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }
    }

    private fun getWorst90sMovies() {
        viewModelScope.launch {
            val holder = worst90sMovies.value

            if(holder.page < holder.pageMax) {
                val newPage = holder.page + 1

                try {
                    val moviesResult = Service.getInstance().client.discover(
                        page = newPage,
                        releaseAfter = "1990-01-01",
                        releaseBefore = "1999-12-31",
                    )

                    processResponse(
                        type = HomeScreenSectionDataHolder.Type.Worst90,
                        moviesResult = moviesResult,
                        oldData = holder.data
                    )
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }
    }

    private fun getWorst80sMovies() {
        viewModelScope.launch {
            val holder = worst80sMovies.value

            if(holder.page < holder.pageMax) {
                val newPage = holder.page + 1

                try {
                    val moviesResult = Service.getInstance().client.discover(
                        page = newPage,
                        releaseAfter = "1980-01-01",
                        releaseBefore = "1989-12-31",
                    )

                    processResponse(
                        type = HomeScreenSectionDataHolder.Type.Worst80,
                        moviesResult = moviesResult,
                        oldData = holder.data
                    )
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }
    }

    private fun getWorstComedyMovies() {
        viewModelScope.launch {
            val holder = worstComedyMovies.value

            if(holder.page < holder.pageMax) {
                val newPage = holder.page + 1

                try {
                    val moviesResult = Service.getInstance().client.discover(
                        genres = mutableListOf(TheMovieDBMovieGenre.Comedy),
                        page = newPage
                    )

                    processResponse(
                        type = HomeScreenSectionDataHolder.Type.WorstComedy,
                        moviesResult = moviesResult,
                        oldData = holder.data
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
                HomeScreenSectionDataHolder.Type.WorstAction -> worstActionMovies.update { newHolder }
                HomeScreenSectionDataHolder.Type.Worst90 -> worst90sMovies.update { newHolder }
                HomeScreenSectionDataHolder.Type.Worst80 -> worst80sMovies.update { newHolder }
                HomeScreenSectionDataHolder.Type.WorstComedy -> worstComedyMovies.update { newHolder }
            }
        }
    }
}