package fr.insideapp.turnipoffkmm.android.logic

import fr.insideapp.turnipoffkmm.network.TheMovieDBClient

class Service private constructor() {
    val client = TheMovieDBClient()

    companion object {
        @Volatile
        private var instance: Service? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: Service().also { instance = it }
            }
    }
}