package com.ggc.webviewonesignaldemo.screen_splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.ggc.webviewonesignaldemo.R
import com.ggc.webviewonesignaldemo.ui.theme.LogoColorOne
import com.ggc.webviewonesignaldemo.ui.theme.LogoColorThree
import com.ggc.webviewonesignaldemo.ui.theme.LogoColorTwo
import com.ggc.webviewonesignaldemo.ui.theme.WebViewOneSignalDemoTheme

@Preview
@Composable
private fun Preview() {
    WebViewOneSignalDemoTheme {
        ScreenSplash()
    }
}

@Composable
fun ScreenSplash() {
    Logo()
}

@Composable
private fun Logo() {
    Text(
        text = "SomeBet",
        fontSize = 35.sp,
        fontFamily = FontFamily(Font(resId = R.font.roboto_bold)),
        style = TextStyle.Default.copy(
            brush = Brush.linearGradient(
                colors = listOf(
                    LogoColorOne,
                    LogoColorTwo,
                    LogoColorTwo,
                    LogoColorTwo,
                    LogoColorTwo,
                    LogoColorTwo,
                    LogoColorThree,
                    LogoColorThree,
                    LogoColorThree,
                    LogoColorOne,
                )
            )
        )
    )
}

