package fr.insideapp.turnipoffkmm.network

private const val baseURL = "https://image.tmdb.org/t/p/"

interface Size {
    val value: String
    fun buildURL(path: String) = "$baseURL$value$path"
}

object PictureSizes {
    enum class Backdrop(override val value: String) : Size {
        W300("w300"),
        W700("w780"),
        W1080("w1280"),
        Original("original")
    }

    enum class Logo(override val value: String) : Size {
        W45("w45"),
        W92("w92"),
        W154("w154"),
        W185("w185"),
        W300("w300"),
        W500("w500"),
        Original("original")
    }

    enum class Poster(override val value: String) : Size {
        W92("w92"),
        W154("w154"),
        W185("w185"),
        W342("w342"),
        W500("w500"),
        W780("w780"),
        Original("original")
    }

    enum class Profile(override val value: String) : Size {
        W92("w45"),
        W185("w185"),
        W632("h632"),
        Original("original")
    }
}