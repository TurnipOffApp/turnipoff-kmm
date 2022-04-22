package fr.insideapp.turnipoffkmm

expect class Platform() {
    val platform: String

    val myLang: String
    val myCountry: String
}