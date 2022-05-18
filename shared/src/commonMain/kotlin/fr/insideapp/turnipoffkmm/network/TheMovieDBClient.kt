package fr.insideapp.turnipoffkmm.network

import fr.insideapp.turnipoffkmm.Platform
import fr.insideapp.turnipoffkmm.model.TheMovieDBMediaType
import fr.insideapp.turnipoffkmm.model.TheMovieDBMovieGenre
import fr.insideapp.turnipoffkmm.model.TheMovieDBResponse
import fr.insideapp.turnipoffkmm.model.TheMovieDBTimeWindow
import fr.insideapp.turnipoffkmm.model.movie.Movie
import fr.insideapp.turnipoffkmm.model.movie.MovieCredits
import fr.insideapp.turnipoffkmm.model.person.Person
import fr.insideapp.turnipoffkmm.model.search.MovieSearchResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import kotlin.native.concurrent.SharedImmutable

@SharedImmutable
expect val defaultPlatformEngine: HttpClientEngine

class TheMovieDBClient {
    private val BASE_URL: String = "api.themoviedb.org"
    private val API_KEY: String = "7aeaa9d72de6df534afb8b71ac7d82eb"

    @OptIn(InternalAPI::class)
    private val BASE_PARAM = StringValues.build {
        append("api_key",API_KEY)
        append("language", Platform().myLang)
        append("region", "US")
        append("adult","false")
    }

    private val httpClient = HttpClient(defaultPlatformEngine) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }

    private fun getRequest(request: TheMovieDBRequest) : HttpRequestBuilder {
        val builder = HttpRequestBuilder()

        builder.url {
            protocol = URLProtocol.HTTPS
            host = BASE_URL
            path("3", *request.pathParams)
            parameters.appendAll(BASE_PARAM)
            parameters.appendAll(request.queryParams)
        }

        return builder
    }

    suspend fun trending(
        mediaType: TheMovieDBMediaType,
        timeWindow: TheMovieDBTimeWindow
    ) : TheMovieDBResponse<MovieSearchResult> {
        val request = getRequest(
            TheMovieDBRequest.Trending(
                mediaType = TheMovieDBRequest.Path.MediaType(mediaType),
                timeWindow = TheMovieDBRequest.Path.TimeWindow(timeWindow)
            )
        )

        return httpClient.get(request).body()
    }

    @OptIn(InternalAPI::class)
    suspend fun discover(
        sortby: String = "vote_average.asc",
        voteCount: Int = 25,
        page: Int,
        genres: List<TheMovieDBMovieGenre>? = null,
        releaseAfter: String? = null,
        releaseBefore: String? = null
    ) : TheMovieDBResponse<MovieSearchResult> {

        val queryParams = mutableListOf(
            TheMovieDBRequest.Query.SortBy(sortby),
            TheMovieDBRequest.Query.VoteCount(voteCount),
            TheMovieDBRequest.Query.Page(page.toString())
        )

        if(genres != null) {
            queryParams.add(TheMovieDBRequest.Query.Genres(genres.joinToString(",") { it.value }))
        }
        if(releaseAfter != null) {
            queryParams.add(TheMovieDBRequest.Query.ReleaseAfter(releaseAfter))
        }
        if(releaseBefore != null) {
            queryParams.add(TheMovieDBRequest.Query.ReleaseBefore(releaseBefore))
        }

        val request = getRequest(
            TheMovieDBRequest.Discover(
                type = TheMovieDBRequest.Discover.Type.Movie,
                queries = queryParams
            )
        )

        return httpClient.get(request).body()
    }

    suspend fun getMovie(movieId: Long): Movie {
        val request = getRequest(
            TheMovieDBRequest.MovieDetails(
                movieId = movieId
            )
        )

        return httpClient.get(request).body()
    }

    suspend fun getMovieCredits(movieId: Long): MovieCredits {
        val request = getRequest(
            TheMovieDBRequest.MovieCredits(
                movieId = movieId
            )
        )

        return httpClient.get(request).body()
    }

    suspend fun getPerson(personId: Long): Person {
        val request = getRequest(
            TheMovieDBRequest.Person(
                personId = personId
            )
        )

        return httpClient.get(request).body()
    }

    suspend fun getPersonCredits(personId: Long): MovieCredits {
        val request = getRequest(
            TheMovieDBRequest.PersonCredits(
                personId = personId
            )
        )

        return httpClient.get(request).body()
    }

    companion object {
        val apiDatePattern = "yyyy-MM-dd"
    }
}