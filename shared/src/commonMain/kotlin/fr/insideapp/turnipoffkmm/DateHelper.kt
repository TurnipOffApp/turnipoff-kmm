package fr.insideapp.turnipoffkmm

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

internal object DateHelper {

    private val apiFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val displayFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)

    fun deserialize(dateString: String): LocalDate? {
        return try {
            if(dateString.isNotBlank()) {
                LocalDate.parse(dateString, apiFormatter)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
    fun serialize(date: LocalDate?): String {
        return date?.format(apiFormatter) ?: ""
    }
}

internal fun LocalDate.formatForDisplay(): String {
    return this.format(DateHelper.displayFormatter)
}