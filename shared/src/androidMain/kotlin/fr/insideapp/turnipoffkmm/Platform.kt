package fr.insideapp.turnipoffkmm

import java.util.*

actual class Platform actual constructor() {
    actual val platform: String = "Android ${android.os.Build.VERSION.SDK_INT}"

    actual val myLang:String
        get() = Locale.getDefault().language

    actual val myCountry:String
        get() = Locale.getDefault().country
}