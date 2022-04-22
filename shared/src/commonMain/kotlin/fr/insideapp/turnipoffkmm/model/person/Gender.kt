package fr.insideapp.turnipoffkmm.model.person

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Gender {
    Unknown,
    Female,
    Male,
    NoBinary;

    companion object {
        operator fun invoke(ordinal: Int) = values().first { it.ordinal == ordinal }
    }
}