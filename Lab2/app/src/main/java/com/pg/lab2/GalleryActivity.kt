package com.pg.lab2

import android.graphics.Canvas
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.pg.lab2.ui.theme.Lab2Theme

class GalleryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab2Theme {
                Column {
                    Greeting("Gallery", fontSize = 40.sp)
                    CustomView()
                }
            }
        }
    }
}

@Composable
fun CustomView() {
    val galleryContext = LocalContext.current
    val intent = (galleryContext as GalleryActivity).intent
    val isColorsChanged = intent.getBooleanExtra("isColorsChanged", false)
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            Painter(context).apply {
                setColorBasedOnCondition(isColorsChanged)
                draw(Canvas())
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    Lab2Theme {
        Column {
            Greeting("Gallery", fontSize = 40.sp)
            CustomView()
        }
    }
}