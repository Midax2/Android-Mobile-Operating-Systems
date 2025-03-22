package com.pg.fileeditor

import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun saveFile(
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
        }
    } catch (e: IOException) {
        Log.e("saveFile", "Error saving file: ${e.message}")
    }
}
