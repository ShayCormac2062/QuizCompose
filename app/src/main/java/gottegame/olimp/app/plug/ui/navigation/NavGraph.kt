package gottegame.olimp.app.plug.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import gottegame.olimp.app.plug.ui.menu.EndScreen
import gottegame.olimp.app.plug.ui.menu.MenuScreen
import gottegame.olimp.app.plug.ui.quiz.QuizScreen
import gottegame.olimp.app.plug.ui.viewmodel.MainViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    val viewModel: MainViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.MenuScreen.route
    ) {
        composable(
            route = Screen.MenuScreen.route
        ) {
            MenuScreen(navController, viewModel)
        }
        composable(
            route = Screen.QuizScreen.route
        ) {
            QuizScreen(navController, viewModel)
        }
        composable(
            route = Screen.EndScreen.route,
        ) {
            EndScreen(navController, viewModel)
        }
    }
}
