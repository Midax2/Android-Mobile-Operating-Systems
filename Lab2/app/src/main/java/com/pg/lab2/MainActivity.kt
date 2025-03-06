@file:Suppress("DEPRECATION")

package com.pg.lab2

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import android.Manifest
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
import androidx.core.content.ContextCompat
import com.pg.lab2.ui.theme.Lab2Theme
import java.io.ByteArrayOutputStream

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
                    SwitchColors(isColorsChanged) { isColorsChanged = it }
                    Row (
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OpenActivity(modifier = Modifier, isColorsChanged, "Gallery")
                        Spacer(modifier = Modifier.weight(1f))
                        OpenActivity(modifier = Modifier, isColorsChanged, "Camera")
                    }
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
fun OpenActivity(modifier: Modifier, isColorsChanged: Boolean, activityName: String) {
    Column(
        modifier = modifier
            .height(400.dp)
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(activityName) {
            "Gallery" -> OpenGallery(modifier, isColorsChanged)
            "Camera" -> OpenCamera()
        }
    }
}

@Composable
fun OpenGallery(modifier: Modifier, isColorsChanged: Boolean) {
    val context = LocalContext.current
    Text(text = "Open Gallery", fontSize = 25.sp)
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
            galleryActivityIntent.putExtra("isColorsChanged", isColorsChanged)
            context.startActivity(galleryActivityIntent)
        },
        modifier = modifier
    )
}

@Composable
fun OpenCamera() {
    val context = LocalContext.current

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val thumbnail: Bitmap? = data?.extras?.get("data") as? Bitmap
            thumbnail?.let {
                val stream = ByteArrayOutputStream()
                it.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray = stream.toByteArray()

                val intent = Intent(context, CameraActivity::class.java)
                intent.putExtra("capturedImage", byteArray)
                context.startActivity(intent)
            }
        }
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(intent)
        }
    }
    Text(text = "Open Camera", fontSize = 25.sp)
    Button(
        content = {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Go to Gallery",
                modifier = Modifier.size(100.dp, 30.dp)
            )
        },
        onClick = {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                == android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraLauncher.launch(intent)
            } else {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        },
        modifier = Modifier.padding(8.dp)
    )
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
        var isColorsChanged by remember { mutableStateOf(false) }
        Column {
            Greeting("Menu", fontSize = 40.sp)
            Spacer(modifier = Modifier.weight(1f))
            SwitchColors(isColorsChanged) { isColorsChanged = it }
            Row (
                modifier = Modifier.fillMaxWidth()
            ) {
                OpenActivity(modifier = Modifier, isColorsChanged, "Gallery")
                Spacer(modifier = Modifier.weight(1f))
                OpenActivity(modifier = Modifier, isColorsChanged, "Camera")
            }
        }
    }
}
