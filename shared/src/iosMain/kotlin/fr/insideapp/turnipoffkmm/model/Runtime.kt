package fr.insideapp.turnipoffkmm.model

actual class Runtime {

    actual val inWholeMinutes: Long = 0L

    actual override fun toString(): String {
        return "test"
    }

    actual companion object {
        actual operator fun invoke(value: Long, unit: RuntimeUnit): Runtime {
            return Runtime()
        }
    }
}