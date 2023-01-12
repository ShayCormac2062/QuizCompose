package gottegame.olimp.app.plug.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gottegame.olimp.app.plug.data.repository.QuizRepositoryImpl
import gottegame.olimp.app.plug.domain.repository.QuizRepository
import gottegame.olimp.app.plug.domain.usecase.GetQuizListUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun provideCardDatabaseRepository(): QuizRepository = QuizRepositoryImpl()

    @Provides
    fun provideGetQuizListUseCase(
        repository: QuizRepository,
        @Named("io") dispatcher: CoroutineDispatcher
    ): GetQuizListUseCase = GetQuizListUseCase(
        repository = repository,
        dispatcher = dispatcher
    )

}
