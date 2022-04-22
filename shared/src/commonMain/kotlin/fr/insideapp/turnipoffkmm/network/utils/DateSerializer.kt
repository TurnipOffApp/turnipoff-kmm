package fr.insideapp.turnipoffkmm.network.utils

import fr.insideapp.turnipoffkmm.model.Date
import fr.insideapp.turnipoffkmm.network.TheMovieDBClient
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal object DateSerializer : KSerializer<Date?> {
    override val descriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: Date?) = encoder.encodeString(value?.serialize(pattern = TheMovieDBClient.apiDatePattern) ?: "")
    override fun deserialize(decoder: Decoder): Date? = Date(dateString = decoder.decodeString(), pattern = TheMovieDBClient.apiDatePattern)
}