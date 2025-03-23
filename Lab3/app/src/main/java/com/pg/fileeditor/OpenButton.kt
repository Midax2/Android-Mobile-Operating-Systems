package com.pg.fileeditor

import android.content.Context
import android.net.Uri
import java.io.BufferedReader
import java.io.InputStreamReader

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