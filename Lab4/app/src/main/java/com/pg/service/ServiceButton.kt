package com.pg.service

import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun ServiceButton(serviceConnection: ServiceConnection) {
    val context = LocalContext.current
    var buttonText by remember { mutableStateOf(stringValueById(context, R.string.main_button_text)) }
    Button(
        onClick = {
            if (buttonText == stringValueById(context, R.string.main_button_text)) {
                buttonText = stringValueById(context, R.string.secondary_button_text)
                val intent = Intent(context, LifeTimeService::class.java)
                context.startService(intent)
                context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
            } else {
                buttonText = stringValueById(context, R.string.main_button_text)
                val intent = Intent(context, LifeTimeService::class.java)
                context.unbindService(serviceConnection)
                context.stopService(intent)
            }
        },
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = buttonText)

    }
}

fun stringValueById(context: Context, @StringRes id: Int): String {
    return context.resources.getString(id)
}