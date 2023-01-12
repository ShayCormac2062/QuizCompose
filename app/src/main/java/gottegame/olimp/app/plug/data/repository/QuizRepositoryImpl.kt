package gottegame.olimp.app.plug.data.repository

import gottegame.olimp.app.R
import gottegame.olimp.app.plug.data.quizlists.BasketballQuiz
import gottegame.olimp.app.plug.data.quizlists.FootballQuiz
import gottegame.olimp.app.plug.data.quizlists.HockeyQuiz
import gottegame.olimp.app.plug.domain.model.QuizModel
import gottegame.olimp.app.plug.domain.repository.QuizRepository

class QuizRepositoryImpl : QuizRepository {

    override suspend fun getQuizList(theme: String): Pair<Int, List<QuizModel>?> =
        when(theme) {
            "Football" -> R.drawable.football to FootballQuiz.quiz
            "Basketball" -> R.drawable.basketball to BasketballQuiz.quiz
            else -> R.drawable.hockey to HockeyQuiz.quiz
        }
}
