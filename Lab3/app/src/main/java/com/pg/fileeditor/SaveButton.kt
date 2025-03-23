package com.pg.fileeditor

import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun saveFile(
    context: Context,
    title: String,
    text: String,
) {
    val fileName = "$title.txt"

    val downloadDirPath =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val file = File(downloadDirPath, fileName)

    try {
        FileOutputStream(file).use { fileOutputStream ->
            fileOutputStream.write(text.toByteArray())
            Log.d("saveFile", "File saved successfully: $file")
            Toast.makeText(
                context,
                "File successfully saved with name: $fileName",
                Toast.LENGTH_SHORT
            ).show()
        }
    } catch (e: IOException) {
        Log.e("saveFile", "Error saving file: ${e.message}")
    }
}
