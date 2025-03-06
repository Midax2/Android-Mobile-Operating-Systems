package com.pg.lab2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pg.lab2.ui.theme.Lab2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab2Theme {
                var isColorsChanged by remember { mutableStateOf(false) }

                Column {
                    Greeting("Menu", fontSize = 40.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    SwitchColors(isColorsChanged) { newState -> isColorsChanged = newState }
                    OpenGallery(modifier = Modifier, isColorsChanged)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, fontSize: TextUnit = 20.sp) {
    Column(
        modifier = modifier
            .height(200.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Laboratory #2", fontSize = fontSize)
        Text(text = name, fontSize = fontSize)
    }
}

@Composable
fun OpenGallery(modifier: Modifier, isColorsChanged: Boolean) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .height(400.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Open Gallery", fontSize = 30.sp)

        Button(
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.gallery),
                    contentDescription = "Go to Gallery",
                    modifier = Modifier.size(100.dp, 30.dp)
                )
            },
            onClick = {
                val galleryActivityIntent = Intent(context, GalleryActivity::class.java)
                galleryActivityIntent.putExtra("isColorsChanged", isColorsChanged) // Passing boolean
                context.startActivity(galleryActivityIntent)
            },
            modifier = modifier
        )
    }
}

@Composable
fun SwitchColors(isColorsChanged: Boolean, onColorChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(fontSize = 25.sp, text = "Change colors:")
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = isColorsChanged,
            onCheckedChange = { newCheckedState -> onColorChange(newCheckedState) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab2Theme {
        var isColorsChanged by remember { mutableStateOf(true) }
        Column {
            Greeting("Menu", fontSize = 40.sp)
            Spacer(modifier = Modifier.weight(1f))
            SwitchColors(isColorsChanged) { isColorsChanged = it }
            OpenGallery(modifier = Modifier, isColorsChanged)
        }
    }
}
