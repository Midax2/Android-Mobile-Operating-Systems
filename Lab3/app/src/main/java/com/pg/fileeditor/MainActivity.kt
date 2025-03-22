package com.pg.fileeditor

import android.os.Bundle
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun EditTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    height: Int = 200,
    description: String
) {
    Column {
        Text(text = description,
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp, start = 5.dp))
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .height(height.dp),
            value = text,
            onValueChange = onValueChange
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FileEditorTheme {
        var text by remember { mutableStateOf("") }
        var title by remember { mutableStateOf("") }
        Column {
            Greeting("Text Editor", fontSize = 40.sp)
            Spacer(modifier = Modifier.height(20.dp))
            EditTextField(text = title, onValueChange = {title = it},
                description = "Title:", height = 50)
            Spacer(modifier = Modifier.height(20.dp))
            EditTextField(text = text, onValueChange = {text = it}, description = "Edit field:")
            Spacer(modifier = Modifier.weight(1.0f))
            BottomButtons(title, text)
        }
    }
}
