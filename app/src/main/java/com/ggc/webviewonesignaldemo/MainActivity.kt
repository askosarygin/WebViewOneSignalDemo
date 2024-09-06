package com.ggc.webviewonesignaldemo

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ggc.webviewonesignaldemo.screen_main.ScreenMain
import com.ggc.webviewonesignaldemo.screen_splash.ScreenSplash
import com.ggc.webviewonesignaldemo.ui.theme.WebViewOneSignalDemoTheme
import com.ggc.webviewonesignaldemo.webview.WebView

class MainActivity : ComponentActivity() {
    var adUrl = ""

    val NavRouteScreenSplash = "ScreenSplash"
    val NavRouteScreenMain = "ScreenMain"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adUrl = getSharedPreferences("AD_URL_STORAGE", Context.MODE_PRIVATE)
            .getString("AD_URL", "https://www.vk.com/")!!


        setContent {
            var showAd by remember { mutableStateOf(true) }
            val navController = rememberNavController()

            WebViewOneSignalDemoTheme {

                NavHost(navController = navController, startDestination = NavRouteScreenSplash) {
                    composable(route = NavRouteScreenSplash) {
                        ScreenSplash()
                    }

                    composable(route = NavRouteScreenMain) {
                        ScreenMain()
                    }
                }

                if (showAd) {
                    WebView(
                        adUrl = adUrl,
                        actionCancelShowAd = {
                            navController.navigate(NavRouteScreenMain)
                            showAd = false
                        },
                        buttonClosePressed = {
                            navController.navigate(NavRouteScreenMain)
                            showAd = false
                        }
                    )
                }
            }
        }
    }
}