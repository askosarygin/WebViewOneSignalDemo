package com.ggc.webviewonesignaldemo.webview

import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient

class WebViewClient(
    private val onPageFinished: (String?) -> Unit,
    private val onReceivedError: (Int) -> Unit
) : WebViewClient() {

    override fun onReceivedError(
        view: WebView?,
        errorCode: Int,
        description: String?,
        failingUrl: String?
    ) {
        super.onReceivedError(view, errorCode, description, failingUrl)
        onReceivedError(errorCode)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)

        CookieManager.getInstance().setAcceptThirdPartyCookies(view, true)

        if (view?.progress == 100) {
            onPageFinished(url)
        }
    }
}