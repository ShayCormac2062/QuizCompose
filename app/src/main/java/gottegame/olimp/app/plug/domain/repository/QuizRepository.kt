package gottegame.olimp.app.plug.domain.repository

import gottegame.olimp.app.plug.domain.model.QuizModel

interface QuizRepository {

    suspend fun getQuizList(theme: String): Pair<Int, List<QuizModel>?>

}
