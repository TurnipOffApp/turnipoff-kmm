package fr.insideapp.turnipoffkmm.network.utils

import fr.insideapp.turnipoffkmm.model.person.Gender
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal object GenderSerializer : KSerializer<Gender> {
    override val descriptor = PrimitiveSerialDescriptor("Gender", PrimitiveKind.INT)
    override fun serialize(encoder: Encoder, value: Gender) = encoder.encodeInt(value.ordinal)
    override fun deserialize(decoder: Decoder): Gender = Gender(decoder.decodeInt())
}