package gottegame.olimp.app.webview

import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewScreen(
    request: String
) {
    var backEnabled = remember { mutableStateOf(false) }
    var webview: WebView? = null
    AndroidView(
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        backEnabled.value = view?.canGoBack() ?: false
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        if (request == url) {
                            backEnabled.value = false
                        }
                    }
                }.apply {
                    val cookieManager = CookieManager.getInstance()
                    cookieManager.setAcceptCookie(true)
                    with(settings) {
                        javaScriptEnabled = true
                        loadWithOverviewMode = true
                        useWideViewPort = true
                        domStorageEnabled = true
                        databaseEnabled = true
                        setSupportZoom(false)
                        allowFileAccess = true
                        allowContentAccess = true
                        loadWithOverviewMode = true
                        useWideViewPort = true
                    }
                }
                loadUrl(request)
                webview = this
            }
        }, update = {
            webview = it
        })
    BackHandler(enabled = true) {
        if (backEnabled.value) {
            webview?.goBack()
        }
    }
}
