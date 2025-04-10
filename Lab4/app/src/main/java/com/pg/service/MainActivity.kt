package com.pg.service

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pg.service.ui.theme.ServiceTheme

class MainActivity : ComponentActivity(), LifeTimeService.ServiceCallback {
    private lateinit var mService: LifeTimeService
    private var mBound by mutableStateOf(false)
    private var mTimePassed by mutableIntStateOf(0)

    override fun onGetResult(intResult: Int) {
        mTimePassed = intResult
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder
        ) {
            val binder = service as LifeTimeService.LocalBinder
            mService = binder.getService()
            mBound = true
            mService.obtainCallback(this@MainActivity)
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
            mTimePassed = 0
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ServiceScreen(serviceConnection, mTimePassed, mBound)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, fontSize: TextUnit = 20.sp) {
    Column(
        modifier = modifier
            .height(200.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Laboratory #4", fontSize = fontSize)
        Text(text = name, fontSize = fontSize)
    }
}

@Composable
fun ServiceScreen(
        serviceConnection: ServiceConnection,
        mTimePassed: Int,
        mBound: Boolean
) {
    ServiceTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Greeting("Services")
            Spacer(modifier = Modifier.weight(1f))
            if (mBound) {
                Text(
                    text = "Time passed: ${mTimePassed}s",
                    fontSize = 20.sp
                )
            }
            ServiceButton(serviceConnection)
        }
    }
}
