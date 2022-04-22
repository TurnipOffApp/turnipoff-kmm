package fr.insideapp.turnipoffkmm.model.search

import fr.insideapp.turnipoffkmm.model.Date
import fr.insideapp.turnipoffkmm.network.utils.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MovieSearchResult(
    @SerialName("id")
    val id: Long,
    @SerialName("adult")
    val isAdult: Boolean,
    @SerialName("title")
    val title: String,
    @SerialName("original_title")
    val originalTitle: String,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("overview")
    val overview: String,
    @SerialName("genre_ids")
    val genres: List<Int>,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName( "release_date")
    @Serializable(DateSerializer::class)
    val releaseDate: Date?,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("video")
    val hasVideo: Boolean,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int
)