package fr.insideapp.turnipoffkmm.model

expect class Date {

    val year: Int

    fun prettyString(): String
    fun serialize(pattern: String): String

    companion object {
        operator fun invoke(dateString: String, pattern: String? = null): Date?
    }
}