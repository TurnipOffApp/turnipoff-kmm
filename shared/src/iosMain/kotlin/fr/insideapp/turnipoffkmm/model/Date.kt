package fr.insideapp.turnipoffkmm.model

import platform.Foundation.*

actual class Date private constructor(private val date: NSDate) {

    actual val year: Int
        get() {
            return NSCalendar.currentCalendar.component(NSCalendarUnitYear, date).toInt()
        }

    actual fun prettyString(): String = displayFormatter.stringFromDate(date)

    actual fun serialize(pattern: String): String {
        val formatter = if(pattern == null) {
            defaultFormatter
        } else {
            val newFormatter = NSDateFormatter()
            newFormatter.dateFormat = pattern
            newFormatter
        }

        return formatter.stringFromDate(date)
    }

    actual companion object {
        private val displayFormatter: NSDateFormatter //= NSDateFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        private val defaultFormatter: NSDateFormatter //= DateTimeFormatter.ofPattern("yyyy-MM-dd")

        init {
            displayFormatter = NSDateFormatter()
            defaultFormatter = NSDateFormatter()

            displayFormatter.dateStyle = NSDateFormatterStyle.MIN_VALUE
            displayFormatter.dateFormat = "yyyy-MM-dd"
        }

        actual operator fun invoke(dateString: String, pattern: String?): Date? {
            val formatter: NSDateFormatter = if(pattern == null) {
                defaultFormatter
            } else {
                val newFormatter = NSDateFormatter()
                newFormatter.dateFormat = pattern
                newFormatter
            }

            val date = try {
                if(dateString.isNotBlank()) {
                    formatter.dateFromString(dateString)
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }

            return date?.let { Date(it) }
        }
    }
}