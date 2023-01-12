package gottegame.olimp.app.remoteconfig.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import gottegame.olimp.app.remoteconfig.domain.repository.DatastoreRepository
import javax.inject.Inject

class DatastoreRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DatastoreRepository {

    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private val gson: Gson = Gson()

    init {
        sharedPreferences =
            context.getSharedPreferences("url", Context.MODE_PRIVATE)
        if (sharedPreferences != null) {
            editor = sharedPreferences?.edit()
        }
        editor?.apply()
    }

    override fun getItem(key: String, type: String): String? {
        val jsonText: String? =
            sharedPreferences?.getString(key, null)
        return gson.fromJson(jsonText, type.javaClass)
    }

    override fun setItem(key: String, item: String) {
        val jsonText: String = gson.toJson(item)
        editor?.putString(key, jsonText)
        editor?.apply()
    }

}
