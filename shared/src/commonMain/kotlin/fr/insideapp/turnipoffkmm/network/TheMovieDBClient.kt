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
import fr.insideapp.turnipoffkmm.network.utils.DateSerializer
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

object TheMovieDBClient {
    private const val BASE_URL: String = "api.themoviedb.org"
    private const val API_KEY: String = "7aeaa9d72de6df534afb8b71ac7d82eb"

    @OptIn(InternalAPI::class)
    private val BASE_PARAM = StringValues.build {
        append("api_key",API_KEY)
        append("language", Platform().myLang)
        append("region", Platform().myCountry)
        append("adult","false")
    }

    private val httpClient = HttpClient() {
        install(JsonFeature) {
            val json = Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            }
            serializer = KotlinxSerializer(json)
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
    ) : TheMovieDBResponse<MovieSearchResult> = withContext(Dispatchers.Default) {
        val request = getRequest(
            TheMovieDBRequest.Trending(
                mediaType = TheMovieDBRequest.Path.MediaType(mediaType),
                timeWindow = TheMovieDBRequest.Path.TimeWindow(timeWindow)
            )
        )

        httpClient.get(request)
    }

    @OptIn(InternalAPI::class)
    suspend fun discover(
        sortby: String,
        voteAverage: String,
        page: Int,
        genres: MutableList<TheMovieDBMovieGenre>? = null,
        releaseAfter: String? = null,
        releaseBefore: String? = null
    ) : TheMovieDBResponse<MovieSearchResult> = withContext(Dispatchers.Default) {

        val queryParams = mutableListOf(
            TheMovieDBRequest.Query.SortBy(sortby),
            TheMovieDBRequest.Query.VoteAverage(voteAverage),
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

        httpClient.get(request)
    }

    suspend fun getMovie(movieId: Long): Movie = withContext(Dispatchers.Default) {
        val request = getRequest(
            TheMovieDBRequest.MovieDetails(
                movieId = movieId
            )
        )

        httpClient.get(request)
    }

    suspend fun getMovieCredits(movieId: Long): MovieCredits = withContext(Dispatchers.Default) {
        val request = getRequest(
            TheMovieDBRequest.MovieCredits(
                movieId = movieId
            )
        )

        httpClient.get(request)
    }

    suspend fun getPerson(personId: Long): Person = withContext(Dispatchers.Default) {
        val request = getRequest(
            TheMovieDBRequest.Person(
                personId = personId
            )
        )

        httpClient.get(request)
    }
}