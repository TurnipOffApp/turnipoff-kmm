package fr.insideapp.turnipoffkmm.android.ui

class Navigator {
    enum class NavTargetRoute(val route: String) {
        Home("home"),
        Movie("movie/{name}/{id}"),
        Person("person/{name}/{id}");
    }
}