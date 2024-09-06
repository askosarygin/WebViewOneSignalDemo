package com.ggc.webviewonesignaldemo.webview

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.ggc.webviewonesignaldemo.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WebView(
    adUrl: String,
    actionCancelShowAd: (canceled: Boolean) -> Unit,
    buttonClosePressed: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var webViewAlpha by remember { mutableFloatStateOf(0f) }
    var iconCloseAlpha by remember { mutableFloatStateOf(0f) }

    Box(
        contentAlignment = Alignment.TopEnd
    ) {
        WebView(
            modifier = Modifier.alpha(alpha = webViewAlpha),
            url = adUrl,
            onPageFinished = { openedUrl ->
                if (redirected(adUrl, openedUrl)) {
                    webViewAlpha = 1f
                    coroutineScope.launch {
                        delay(3000)
                        iconCloseAlpha = 1f
                    }
                } else {
                    actionCancelShowAd(true)
                }
            },
            onReceivedError = { errorCode ->
                if (errorCode == -2) {
                    actionCancelShowAd(true)
                }
            }
        )

        Image(
            modifier = Modifier
                .padding(top = 20.dp, end = 20.dp)
                .alpha(alpha = iconCloseAlpha)
                .clickable(
                    enabled = iconCloseAlpha == 1f,
                    onClick = buttonClosePressed
                ),
            painter = painterResource(id = R.drawable.icon_close),
            contentDescription = ""
        )
    }
}

private fun redirected(url1: String?, url2: String?): Boolean {
    val urlOne = Uri.parse(url1).host?.replace("www.", "")
    val urlTwo = Uri.parse(url2).host?.replace("www.", "")
    return urlOne != urlTwo
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun WebView(
    modifier: Modifier = Modifier,
    url: String,
    onPageFinished: (openedUrl: String?) -> Unit = {},
    onReceivedError: (errorCode: Int) -> Unit = {}
) {
    var webView: WebView? by remember {
        mutableStateOf(null)
    }
    AndroidView(
        modifier = modifier,
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    loadsImagesAutomatically = true
                    mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                }
                webViewClient = WebViewClient(
                    onPageFinished,
                    onReceivedError
                )
                loadUrl(url)
                webView = this
            }
        },
        update = {
            it.loadUrl(url)
        }
    )
    BackHandler(enabled = true) {
        webView?.goBack()
    }
}