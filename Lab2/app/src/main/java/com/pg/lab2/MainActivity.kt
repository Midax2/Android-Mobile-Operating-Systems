package com.pg.lab2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pg.lab2.ui.theme.Lab2Theme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab2Theme {
                Column {
                    Greeting("Gallery", fontSize = 40.sp)
                    OpenGallery(modifier = Modifier)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, fontSize: TextUnit = 20.sp) {
    Column(
        modifier = modifier
            .height(400.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Laboratory #2",
            modifier = modifier,
            fontSize = fontSize
        )
        Text(
            text = " $name",
            modifier = modifier,
            fontSize = fontSize
        )
    }
}

@Composable
fun OpenGallery(modifier: Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .height(400.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Open Gallery",
            modifier = modifier,
            fontSize = 30.sp
        )
        Button(
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.gallery),
                    contentDescription = "Go to Gallery",
                    modifier = Modifier.size(100.dp, 30.dp)
                )
            },
            onClick = {
                val galleryActivityIntent =
                    Intent(context, GalleryActivity::class.java)
                context.startActivity(galleryActivityIntent)
            },
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab2Theme {
        Column {
            Greeting("Gallery", fontSize = 40.sp)
            Spacer(modifier = Modifier.weight(1f))
            OpenGallery(modifier = Modifier)
        }
    }
}