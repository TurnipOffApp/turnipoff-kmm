package fr.insideapp.turnipoffkmm.model.person

import fr.insideapp.turnipoffkmm.model.Date
import fr.insideapp.turnipoffkmm.network.utils.DateSerializer
import fr.insideapp.turnipoffkmm.network.utils.GenderSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Person(
    @SerialName("id")
    val id: Long,
    @SerialName("imdb_id")
    val imdbID: String?,
    @SerialName("name")
    val name: String,
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("profile_path")
    val profilePath: String?,
    @SerialName("also_known_as")
    val otherNames: List<String>,
    @SerialName("biography")
    val biography: String?,
    @SerialName("deathday")
    @Serializable(DateSerializer::class)
    val deathday: Date?,
    @SerialName("birthday")
    @Serializable(DateSerializer::class)
    val birthday: Date?,
    @SerialName("known_for_department")
    val department: String,
    @SerialName("place_of_birth")
    val placeOfBirth: String?,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("homepage")
    val homepage: String?,
    @SerialName("gender")
    @Serializable(GenderSerializer::class)
    val gender: Gender,
) {
    val birthDate: String?
        get() = birthday?.let { it.prettyString() }

    val deathDate: String?
        get() = deathday?.let { it.prettyString() }
}