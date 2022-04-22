package fr.insideapp.turnipoffkmm.network.utils

import fr.insideapp.turnipoffkmm.DateHelper
import java.time.LocalDate
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.lang.Exception
import java.time.format.DateTimeFormatter

internal object DateSerializer : KSerializer<LocalDate?> {


    override val descriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: LocalDate?) = encoder.encodeString(DateHelper.serialize(value))
    override fun deserialize(decoder: Decoder): LocalDate? = DateHelper.deserialize(decoder.decodeString())
}