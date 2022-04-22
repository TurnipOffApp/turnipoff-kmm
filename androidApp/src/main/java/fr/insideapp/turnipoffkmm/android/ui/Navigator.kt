package fr.insideapp.turnipoffkmm.android.ui

class Navigator {
    enum class NavTargetRoute(val route: String) {
        Home("home"),
        Movie("movie/{id}"),
        Person("person/{id}");
    }
}