package fr.insideapp.turnipoffkmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fr.insideapp.turnipoffkmm.android.ui.screens.movie.MovieScreen
import fr.insideapp.turnipoffkmm.android.ui.theme.TurnipOffTheme
import fr.insideapp.turnipoffkmm.Greeting
import fr.insideapp.turnipoffkmm.android.ui.Navigator
import fr.insideapp.turnipoffkmm.android.ui.screens.credit.PersonScreen
import fr.insideapp.turnipoffkmm.android.ui.screens.home.HomeScreen

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            TurnipOffTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold {
                        NavigationComponent(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun NavigationComponent(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Navigator.NavTargetRoute.Home.route
    ) {
        composable(
            route = Navigator.NavTargetRoute.Home.route
        ) {
            HomeScreen(navController)
        }
        composable(
            route = Navigator.NavTargetRoute.Movie.route,
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("id") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            MovieScreen(
                navController = navController,
                movieName = backStackEntry.arguments?.getString("name") ?: "Movie",
                movieId = backStackEntry.arguments?.getLong("id") ?: 0L)
        }
        composable(
            route = Navigator.NavTargetRoute.Person.route,
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("id") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            PersonScreen(
                navController = navController,
                personName = backStackEntry.arguments?.getString("name") ?: "Person",
                personId = backStackEntry.arguments?.getLong("id") ?: 0L)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TurnipOffTheme {
        Greeting("Android")
    }
}
