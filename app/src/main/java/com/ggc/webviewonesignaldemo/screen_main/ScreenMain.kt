package com.ggc.webviewonesignaldemo.screen_main

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ggc.webviewonesignaldemo.R
import com.ggc.webviewonesignaldemo.ui.theme.WebViewOneSignalDemoTheme
import com.onesignal.OneSignal

@Preview
@Composable
private fun Preview() {
    WebViewOneSignalDemoTheme {
        ScreenMain()
    }
}

@Composable
fun ScreenMain() {
    val context = LocalContext.current
    var oneSignalAppIdTextField by remember { mutableStateOf("") }
    var adUrlTextField by remember { mutableStateOf("") }
    var connectToOneSignalButtonEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(space = 10.dp)
    ) {
        EnterOneSignalAppIdMessage()

        EnterOneSignalAppIdTextField(
            textField = oneSignalAppIdTextField,
            onValueChange = { newText ->
                oneSignalAppIdTextField = newText
            }
        )

        ConnectToOneSignalButton(
            enabled = connectToOneSignalButtonEnabled,
            onClick = {

                if (oneSignalAppIdTextField.isNotBlank()) {
                    OneSignal.setAppId(oneSignalAppIdTextField)
                    OneSignal.initWithContext(context)
                    OneSignal.promptForPushNotifications()

                    Toast.makeText(
                        context,
                        context.resources.getText(R.string.connected_one_signal),
                        Toast.LENGTH_LONG
                    ).show()
                    oneSignalAppIdTextField = ""
                } else {
                    Toast.makeText(
                        context,
                        context.resources.getText(R.string.empty_text_field),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )

        Spacer(modifier = Modifier.height(height = 35.dp))

        EnterYourAdUrlMessage()

        EnterYourAdUrlTextField(
            textField = adUrlTextField,
            onValueChange = {newText ->
                adUrlTextField = newText
            }
        )

        ChangeAdUrlButton(
            onClick = {
                context
                    .getSharedPreferences("AD_URL_STORAGE", Context.MODE_PRIVATE)
                    .edit()
                    .putString("AD_URL", adUrlTextField)
                    .apply()

                Toast.makeText(
                    context,
                    context.resources.getText(R.string.url_saved),
                    Toast.LENGTH_SHORT
                ).show()

                adUrlTextField = ""
            }
        )
    }
}

@Composable
private fun EnterOneSignalAppIdMessage() {
    AppText(text = stringResource(id = R.string.enter_your_one_signal_app_id_to_send_notifications))
}

@Composable
private fun EnterOneSignalAppIdTextField(
    textField: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = textField,
        onValueChange = onValueChange,
        placeholder = {
            if (textField.isEmpty()) {
                Text(text = stringResource(id = R.string.enter_one_signal_app_id_here))
            }
        }
    )
}

@Composable
private fun ConnectToOneSignalButton(
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        content = {
            Text(text = stringResource(id = R.string.connect_to_one_signal))
        }
    )
}

@Composable
private fun EnterYourAdUrlMessage() {
    AppText(text = stringResource(id = R.string.enter_your_ad_url_description))
}

@Composable
private fun EnterYourAdUrlTextField(
    textField: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = textField,
        onValueChange = onValueChange,
        placeholder = {
            if (textField.isEmpty()) {
                Text(text = stringResource(id = R.string.enter_your_ad_url_here))
            }
        }
    )
}

@Composable
private fun ChangeAdUrlButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        content = {
            Text(text = stringResource(id = R.string.change_ad_url))
        }
    )
}


@Composable
private fun AppText(
    text: String
) {
    Text(
        text = text,
        fontSize = 18.sp
    )
}