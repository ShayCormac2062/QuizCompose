package gottegame.olimp.app.plug.domain.usecase

import gottegame.olimp.app.plug.domain.repository.QuizRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class GetQuizListUseCase @Inject constructor(
    private val repository: QuizRepository,
    @Named("io") private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(theme: String) =
        withContext(dispatcher) {
            repository.getQuizList(theme)
        }

}
