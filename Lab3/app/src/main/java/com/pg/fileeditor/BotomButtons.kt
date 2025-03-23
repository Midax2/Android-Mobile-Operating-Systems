package com.pg.fileeditor

import android.app.Activity
import android.content.Intent
import android.os.Environment
import android.provider.DocumentsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomButtons(
    title: String = "",
    text: String = "",
    onTitleChange: (String) -> Unit,
    onTextChange: (String) -> Unit
) {
    val context = LocalContext.current
    var fileContent by remember { mutableStateOf("") }
    var selectedFileName by remember { mutableStateOf("") }

    val openFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            if (uri != null) {
                try {
                    val content = readTextFromUri(context, uri)
                    fileContent = content
                    selectedFileName = getFileName(context, uri) ?: ""
                    selectedFileName = selectedFileName.substringBeforeLast(".")
                    onTitleChange(selectedFileName)
                    onTextChange(fileContent)

                } catch (e: Exception) {
                    e.printStackTrace()

                }
            }
        }
    }

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
            ActionButton(
                text = "Save",
                modifier = Modifier.weight(1f),
                onClick = { saveFile(title, text) }
            )
            ActionButton(
                text = "Open",
                modifier = Modifier.weight(1f),
                onClick = {
                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "text/plain"
                        putExtra(DocumentsContract.EXTRA_INITIAL_URI, Environment.DIRECTORY_DOWNLOADS)

                    }
                    openFileLauncher.launch(intent)
                }
            )
            ActionButton(text = "History", modifier = Modifier.weight(1f), onClick = { /*TODO*/ })
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

@Preview(showBackground = true)
@Composable
fun BottomButtonsPreview() {
    BottomButtons(onTitleChange = {}, onTextChange = {})
}