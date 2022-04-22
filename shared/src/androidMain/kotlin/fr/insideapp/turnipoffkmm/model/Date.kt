package fr.insideapp.turnipoffkmm.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

actual class Date private constructor(private val date: LocalDate) {
    actual val year: Int
        get() = date.year

    actual fun prettyString(): String = date.format(displayFormatter) ?: "N/A"

    actual companion object {

        private val displayFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        private val defaultFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        actual operator fun invoke(dateString: String, pattern: String?): Date? {

            val formatter = if(pattern == null) {
                defaultFormatter
            } else {
                DateTimeFormatter.ofPattern(pattern)
            }

            val date = try {
                if(dateString.isNotBlank()) {
                    LocalDate.parse(dateString, formatter)
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }

            return date?.let { Date(it) }
        }
    }

    actual fun serialize(pattern: String): String {
        val formatter = if(pattern == null) {
            defaultFormatter
        } else {
            DateTimeFormatter.ofPattern(pattern)
        }

        return date.format(formatter)
    }
}