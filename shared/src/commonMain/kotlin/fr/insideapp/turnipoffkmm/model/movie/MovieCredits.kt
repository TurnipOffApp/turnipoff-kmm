package fr.insideapp.turnipoffkmm.model.movie

import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuid4
import com.benasher44.uuid.uuidOf
import fr.insideapp.turnipoffkmm.model.person.Gender
import fr.insideapp.turnipoffkmm.network.utils.GenderSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieCredits(
    @SerialName("id")
    val id: Long,
    @SerialName("cast")
    val cast: List<Credit>,
    @SerialName("crew")
    val crew: List<Credit>

) {
    @Serializable
    data class Credit(
        @SerialName("id")
        val id: Long,
        @SerialName("adult")
        val adult: Boolean,
        @SerialName("gender")
        @Serializable(GenderSerializer::class)
        val gender: Gender = Gender.Unknown,
        @SerialName("known_for_department")
        val knownForDepartment: String = "",
        @SerialName("name")
        val name: String = "",
        @SerialName("title")
        val movieTitle: String = "",
        @SerialName("original_name")
        val originalName: String = "",
        @SerialName("popularity")
        val popularity: Double,
        @SerialName("profile_path")
        val profilePath: String? = null,
        @SerialName("poster_path")
        val posterPath: String? = null,
        @SerialName("cast_id")
        val castID: Long? = null,
        @SerialName("character")
        val character: String? = "",
        @SerialName("credit_id")
        val creditID: String,
        @SerialName("order")
        val order: Long = 0,
        @SerialName("department")
        val department: String = "",
        @SerialName("job")
        val job: String? = "",
        @SerialName("vote_average")
        val average: Float = 0.0f
    ) {
        val title: String
            get() = if(!name.isNullOrBlank()) {
                name
            } else if(!movieTitle.isNullOrBlank()) {
                movieTitle
            } else {
                "No name"
            }

        val subtitle: String
            get() = if(!character.isNullOrBlank()) {
                character
            } else if(!job.isNullOrBlank()) {
                job
            } else {
                "No role"
            }

        val internalID = uuid4().toString()
    }
}