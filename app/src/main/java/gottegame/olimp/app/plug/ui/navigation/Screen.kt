package gottegame.olimp.app.plug.ui.navigation

sealed class Screen(val route: String) {
    object MenuScreen: Screen("menu_screen")
    object QuizScreen: Screen("quiz_screen")
    object EndScreen: Screen("end_screen")
}
