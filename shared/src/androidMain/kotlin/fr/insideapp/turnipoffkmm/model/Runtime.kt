package fr.insideapp.turnipoffkmm.model

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

actual class Runtime(private val duration: Duration) {

    actual val inWholeMinutes: Long = duration.inWholeMinutes

    actual override fun toString(): String {
        return duration.toString()
    }

    actual companion object {
        actual operator fun invoke(value: Long, unit: RuntimeUnit): Runtime {
            val duration = when(unit) {
                RuntimeUnit.MINUTES -> value.minutes
                else -> value.seconds
            }

            return Runtime(duration)
        }
    }
}