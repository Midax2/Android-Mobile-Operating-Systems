package com.pg.fileeditor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pg.fileeditor.ui.theme.FileEditorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FileEditorTheme {
                Column {
                    Greeting("Text Editor", fontSize = 40.sp)

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
        Text(text = "Laboratory #3", fontSize = fontSize)
        Text(text = name, fontSize = fontSize)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FileEditorTheme {
        Column {
            Greeting("Text Editor", fontSize = 40.sp)

        }
    }
}
