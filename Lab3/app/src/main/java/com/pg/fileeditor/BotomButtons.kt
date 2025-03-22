package com.pg.fileeditor

import android.os.Environment
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Composable
fun BottomButtons(title: String = "", text: String = "") {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ActionButton(text = "Save", modifier = Modifier.weight(1f), onClick = { saveFile(title, text)})
            ActionButton(text = "Open File", modifier = Modifier.weight(1f), onClick = { /*TODO*/ })
            ActionButton(text = "History Page", modifier = Modifier.weight(1f), onClick = { /*TODO*/ })
        }
    }
}

@Composable
fun ActionButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        shape = RectangleShape,
        modifier = modifier.height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(text = text, color = Color.White)
    }
}

fun saveFile(title: String, text: String) {
    val fileName = "$title.txt"
    val downloadDirPath =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val file = File(downloadDirPath, fileName)

    try {
        FileOutputStream(file).use { fileOutputStream ->
            fileOutputStream.write(text.toByteArray())
            Log.d("saveFile", "File saved successfully: $file")
        }
    } catch (e: IOException) {
        Log.e("saveFile", "Error saving file: ${e.message}")
    }
}


@Preview(showBackground = true)
@Composable
fun BottomButtonsPreview() {
    BottomButtons()
}