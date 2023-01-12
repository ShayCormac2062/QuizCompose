package gottegame.olimp.app.remoteconfig.ui.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.telephony.TelephonyManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import gottegame.olimp.app.remoteconfig.domain.usecase.IRemoteConfigValueUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val useCase: IRemoteConfigValueUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState: MutableStateFlow<Event?> =
        MutableStateFlow(null)
    val uiState: StateFlow<Event?> = _uiState

    fun getUrl() {
        viewModelScope.launch {
            val url = useCase.readConfigValue()
                ?: if (!checkIsEmu() && isHasSim()) useCase.remoteUrl() else ""
            if (!isOnline()) {
                _uiState.value = Event.ErrorEvent
            } else if (url == null || url == "") {
                delay(2000)
                _uiState.value = when (url) {
                    null -> Event.ErrorEvent
                    else -> {
                        Event.SuccessEvent(url)
                    }
                }
            } else _uiState.value = Event.SuccessEvent(url)
        }
    }

    fun saveUrl(url: String) {
        viewModelScope.launch {
            useCase.saveConfigValue(url)
        }
    }

    private fun isOnline(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
        }
        return false
    }

    private fun checkIsEmu(): Boolean {
        val phoneModel = Build.MODEL
        val buildProduct = Build.PRODUCT
        val buildHardware = Build.HARDWARE
        val brand: String = Build.BRAND
        var result = (Build.FINGERPRINT.startsWith("generic")
                || phoneModel.contains("google_sdk")
                || phoneModel.lowercase(Locale.getDefault()).contains("droid4x")
                || phoneModel.contains("Emulator")
                || phoneModel.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || buildHardware == "goldfish"
                || brand.contains("google")
                || buildHardware == "vbox86"
                || buildProduct == "sdk"
                || buildProduct == "google_sdk"
                || buildProduct == "sdk_x86"
                || buildProduct == "vbox86p"
                || Build.BOARD.lowercase(Locale.getDefault()).contains("nox")
                || Build.BOOTLOADER.lowercase(Locale.getDefault()).contains("nox")
                || buildHardware.lowercase(Locale.getDefault()).contains("nox")
                || buildProduct.lowercase(Locale.getDefault()).contains("nox"))
        if (result) return true
        result = result or (brand.startsWith("generic") &&
                Build.DEVICE.startsWith("generic"))
        if (result) return true
        result = result or (buildProduct == "google_sdk")
        return result
    }

    private fun isHasSim(): Boolean {
        val tm = context.getSystemService(
            Context.TELEPHONY_SERVICE
        ) as TelephonyManager
        return tm.simState != TelephonyManager.SIM_STATE_ABSENT
    }
}
