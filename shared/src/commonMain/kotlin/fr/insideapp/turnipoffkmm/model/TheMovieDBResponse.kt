package fr.insideapp.turnipoffkmm.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.native.concurrent.SharedImmutable

@Serializable
data class TheMovieDBResponse<T>(
    @SerialName("page")
    val page: Int,
    @SerialName("total_pages")
    val totalPage: Int,
    @SerialName("total_results")
    val totalResults: Int,
    @SerialName("results")
    val results: List<T>
)
