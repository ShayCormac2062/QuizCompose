package gottegame.olimp.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import dagger.hilt.android.AndroidEntryPoint
import gottegame.olimp.app.plug.ui.navigation.SetupNavGraph
import gottegame.olimp.app.remoteconfig.ui.screen.ConfigProgressBar
import gottegame.olimp.app.remoteconfig.ui.screen.ErrorMessage
import gottegame.olimp.app.remoteconfig.ui.viewmodel.ConfigViewModel
import gottegame.olimp.app.remoteconfig.ui.viewmodel.Event
import gottegame.olimp.app.ui.theme.FootballQuizTheme
import gottegame.olimp.app.webview.WebViewScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = Firebase.remoteConfig.apply {
            setConfigSettingsAsync(
                FirebaseRemoteConfigSettings.Builder()
                    .setMinimumFetchIntervalInSeconds(3600)
                    .build()
            )
        }
        config.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.e("FUCK", "Config params updated: $updated")
                } else {
                    Log.e("FUCK", "Fetch failed")
                }
                setContent {
                    FootballQuizTheme {
                        val configViewModel: ConfigViewModel = viewModel()
                        val state = configViewModel.uiState.collectAsState()
                        when (state.value) {
                            is Event.ErrorEvent -> ErrorMessage {
                                configViewModel.getUrl()
                            }
                            is Event.SuccessEvent -> MainScreen(
                                configViewModel,
                                (state.value as Event.SuccessEvent).url
                            )
                            else -> {
                                ConfigProgressBar()
                                configViewModel.getUrl()
                            }
                        }
                    }
                }
            }
    }
}

@Composable
private fun MainScreen(
    configViewModel: ConfigViewModel,
    url: String
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.sports),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        if (url == "") {
            val navController = rememberNavController()
            SetupNavGraph(
                navController = navController
            )
        } else {
            configViewModel.saveUrl(url)
            WebViewScreen(url)
        }
    }
}
