package gottegame.olimp.app.remoteconfig.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gottegame.olimp.app.remoteconfig.data.repository.DatastoreRepositoryImpl
import gottegame.olimp.app.remoteconfig.domain.repository.DatastoreRepository
import gottegame.olimp.app.remoteconfig.domain.usecase.IRemoteConfigValueUseCase
import gottegame.olimp.app.remoteconfig.domain.usecase.RemoteConfigValueUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ): DatastoreRepository = DatastoreRepositoryImpl(context)

    @Provides
    fun provideRemoteConfigValueUseCase(
        repository: DatastoreRepository,
        @Named("io_") dispatcher: CoroutineDispatcher
    ): IRemoteConfigValueUseCase = RemoteConfigValueUseCase(repository, dispatcher)

}
