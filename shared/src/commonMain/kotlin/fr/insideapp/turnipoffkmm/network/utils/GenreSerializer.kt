package fr.insideapp.turnipoffkmm.network.utils

import fr.insideapp.turnipoffkmm.model.TheMovieDBMovieGenre
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal class GenreSerializer(private val dataSerializer : KSerializer<TheMovieDBMovieGenre>) : KSerializer<List<TheMovieDBMovieGenre>> {

    override val descriptor = ListSerializer(dataSerializer).descriptor

    override fun serialize(encoder: Encoder, value: List<TheMovieDBMovieGenre>) { }

    override fun deserialize(decoder: Decoder): List<TheMovieDBMovieGenre> {
        val arrayList = arrayListOf<Int>()
        try {
            val startIndex = arrayList.size
            val messageBuilder = StringBuilder()
            val compositeDecoder = decoder.beginStructure(descriptor)
            while (true) {
                val index = compositeDecoder.decodeElementIndex(descriptor) // fails here on number 2
                if (index == CompositeDecoder.DECODE_DONE) {
                    break
                }
                try {
                    arrayList.add(index, compositeDecoder.decodeIntElement(descriptor, startIndex + index)) //(descriptor, startIndex + index, dataSerializer))
                } catch (exception: Exception) {
                    exception.printStackTrace() // falls here when "invalid_int" is parsed, it's ok
                }
            }
            compositeDecoder.endStructure(descriptor)
            if (messageBuilder.isNotBlank()) {
                println(messageBuilder.toString())
            }
        } catch (exception: Exception) {
            exception.printStackTrace() // falls here on number 2
        }
        return arrayList.map { TheMovieDBMovieGenre(it) }
    }
}