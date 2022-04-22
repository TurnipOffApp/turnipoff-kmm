package fr.insideapp.turnipoffkmm.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TheMovieDBMovieGenre(val value: String) {
    @SerialName("28")
    Action("28"),

    @SerialName("12")
    Adventure("12"),

    @SerialName("16")
    Animation("16"),

    @SerialName("35")
    Comedy("35"),

    @SerialName("80")
    Crime("80"),

    @SerialName("99")
    Documentary("99"),

    @SerialName("18")
    Drame("18"),

    @SerialName("10751")
    Familial("10751"),

    @SerialName("14")
    Fantastique("14"),

    @SerialName("36")
    History("36"),

    @SerialName("27")
    Horror("27"),

    @SerialName("10402")
    Music("10402"),

    @SerialName("9648")
    Mystery("9648"),

    @SerialName("10749")
    Romance("10749"),

    @SerialName("878")
    ScienceFiction("878"),

    @SerialName("10770")
    Telefilm("10770"),

    @SerialName("53")
    Thriller("53"),

    @SerialName("10752")
    War("10752"),

    @SerialName("37")
    Western("37");

    companion object {
        operator fun invoke(id: String) = values().first { it.value == id }
        operator fun invoke(id: Int) = values().first { it.value == id.toString() }
    }
}