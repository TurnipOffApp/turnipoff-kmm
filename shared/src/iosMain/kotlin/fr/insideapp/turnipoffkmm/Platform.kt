package fr.insideapp.turnipoffkmm

import platform.Foundation.NSLocale
import platform.Foundation.countryCode
import platform.Foundation.currentLocale
import platform.Foundation.languageCode
import platform.UIKit.UIDevice

actual class Platform actual constructor() {
    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    actual val myLang:String
        get() = NSLocale.currentLocale.languageCode

    actual val myCountry:String
        get() = NSLocale.currentLocale.countryCode ?: "EN"
}