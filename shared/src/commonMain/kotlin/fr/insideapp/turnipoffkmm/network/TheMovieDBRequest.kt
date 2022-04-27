package fr.insideapp.turnipoffkmm.network

import fr.insideapp.turnipoffkmm.model.TheMovieDBMediaType
import fr.insideapp.turnipoffkmm.model.TheMovieDBTimeWindow
import io.ktor.util.*

internal sealed class TheMovieDBRequest {
    class Trending(val mediaType: Path.MediaType, val timeWindow: Path.TimeWindow): TheMovieDBRequest()
    class Discover(val type: Type, val queries: List<Query>): TheMovieDBRequest() {
        enum class Type(val value: String) {
            Movie("movie")
        }
    }
    class MovieDetails(val movieId: Long): TheMovieDBRequest()
    class MovieCredits(val movieId: Long): TheMovieDBRequest()
    class Person(val personId: Long): TheMovieDBRequest()
    class PersonCredits(val personId: Long): TheMovieDBRequest()

    val pathParams: Array<String>
        get() = when(this) {
            is Trending -> arrayOf("trending", mediaType.value, timeWindow.value)
            is Discover -> arrayOf("discover", type.value)
            is MovieDetails -> arrayOf("movie", movieId.toString())
            is MovieCredits -> arrayOf("movie", movieId.toString(), "credits")
            is Person -> arrayOf("person", personId.toString())
            is PersonCredits -> arrayOf("person", personId.toString(), "movie_credits")
        }

    @OptIn(InternalAPI::class)
    val queryParams: StringValues
        get() = when(this) {
            is Trending, is MovieDetails, is MovieCredits, is Person, is PersonCredits -> StringValues.Empty
            is Discover -> StringValues.build {
                queries.forEach { query ->
                    append(query.key, query.value)
                }
            }
        }

    sealed class Query(val key: String, val value: String) {
        class SortBy(value: String): Query("sort_by", value)
        class VoteCount(value: Int): Query("vote_count.gte", value.toString())
        class Page(value: String): Query("page", value)
        class Genres(value: String): Query("with_genres", value)
        class ReleaseAfter(value: String): Query("release_date.gte", value)
        class ReleaseBefore(value: String): Query("release_date.lte", value)

        val query: Pair<String, String>
            get() = Pair(key, value)
    }

    sealed class Path {
        class MediaType(val mediaType: TheMovieDBMediaType): Path()
        class TimeWindow(val timeWindow: TheMovieDBTimeWindow): Path()

        val value: String
            get() = when(this) {
                is MediaType -> mediaType.value
                is TimeWindow -> timeWindow.value
            }
    }
}