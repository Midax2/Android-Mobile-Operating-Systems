package com.pg.fileeditor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import java.io.BufferedReader
import java.io.InputStreamReader

class FileManager {
    private lateinit var openFileLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>

    @Composable
    fun registerOpenFileLauncher(onFileOpened: (Uri?) -> Unit): ManagedActivityResultLauncher<Intent, ActivityResult> {
        openFileLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                onFileOpened(result.data?.data)
            } else {
                onFileOpened(null)
            }
        }
        return openFileLauncher
    }

    fun readTextFromUri(context: Context, uri: Uri): String {
        val stringBuilder = StringBuilder()
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    stringBuilder.append(line).append('\n')
                    line = reader.readLine()
                }
            }
        }
        return stringBuilder.toString()
    }

    fun getFileName(context: Context, uri: Uri): String? {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndexOrThrow("_display_name")
                return it.getString(nameIndex)
            }
        }
        return null
    }
}