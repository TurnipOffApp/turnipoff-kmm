package fr.insideapp.turnipoffkmm.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TheMovieDBTimeWindow(val value: String) {
    @SerialName("day")
    Day("day"),

    @SerialName("week")
    Week("week");

    val jsonValue: String
        get() = this.value
}