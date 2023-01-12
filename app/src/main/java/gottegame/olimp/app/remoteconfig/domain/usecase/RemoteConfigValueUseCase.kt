package gottegame.olimp.app.remoteconfig.domain.usecase

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import gottegame.olimp.app.R
import gottegame.olimp.app.remoteconfig.domain.repository.DatastoreRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

interface IRemoteConfigValueUseCase {
    suspend fun saveConfigValue(value: String)
    suspend fun readConfigValue(): String?
    suspend fun remoteUrl(): String?
}

class RemoteConfigValueUseCase @Inject constructor(
    private val dataStore: DatastoreRepository,
    @Named("io_") private val dispatcher: CoroutineDispatcher
) : IRemoteConfigValueUseCase {

    override suspend fun saveConfigValue(value: String) {
        dataStore.setItem("url", value)
    }

    override suspend fun readConfigValue(): String? =
        withContext(dispatcher) {
            dataStore.getItem("url", "string")
        }

    override suspend fun remoteUrl(): String {
        val result = Firebase.remoteConfig.getString("url")
        return result
    }
}
