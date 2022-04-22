package fr.insideapp.turnipoffkmm.network.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

internal object RuntimeSerializer : KSerializer<Duration> {
    override val descriptor = PrimitiveSerialDescriptor("Runtime", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: Duration) = encoder.encodeLong(value.inWholeMinutes)
    override fun deserialize(decoder: Decoder): Duration {
        val duration = decoder.decodeLong()
        return duration.minutes
    }
}