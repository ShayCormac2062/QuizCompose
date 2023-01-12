package gottegame.olimp.app.remoteconfig.domain.repository

interface DatastoreRepository {
    fun getItem(key: String, type: String): String?
    fun setItem(key: String, item: String)
}
