package fr.insideapp.turnipoffkmm.model

enum class RuntimeUnit {
    /**  Time unit representing one nanosecond, which is 1/1000 of a microsecond. */
    NANOSECONDS,
    /** Time unit representing one microsecond, which is 1/1000 of a millisecond. */
    MICROSECONDS,
    /** Time unit representing one millisecond, which is 1/1000 of a second. */
    MILLISECONDS,
    /** Time unit representing one second. */
    SECONDS,
    /** Time unit representing one minute. */
    MINUTES,
    /** Time unit representing one hour. */
    HOURS,
    /** Time unit representing one day, which is always equal to 24 hours. */
    DAYS;
}

expect class Runtime {

    val inWholeMinutes: Long

    override fun toString(): String

    companion object {
        operator fun invoke(value: Long, unit: RuntimeUnit): Runtime
    }
}