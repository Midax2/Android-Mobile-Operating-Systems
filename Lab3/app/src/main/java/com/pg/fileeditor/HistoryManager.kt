package com.pg.fileeditor

import android.content.Context
import android.net.Uri
import android.widget.Toast
import java.io.File

class HistoryManager {
    data class HistoryItem(val uri: Uri, val fileName: String)

    var lastOpenedFile: HistoryItem? = null
        private set

    fun updateLastOpenedFile(uri: Uri, fileManager: FileManager, context: Context) {
        val fileName = fileManager.getFileName(context, uri) ?: "Unknown"
        lastOpenedFile = HistoryItem(uri, fileName)
    }

    fun showHistory(context: Context) {
        if (lastOpenedFile != null) {
            val uri = lastOpenedFile!!.uri
            val path = getPathFromUri(uri)
            val message = if (path != null) {
                "Last opened file: ${lastOpenedFile!!.fileName}\nPath: $path"
            } else {
                "Last opened file: ${lastOpenedFile!!.fileName}\nURI: $uri"
            }
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(
                context,
                "No files opened yet.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun getPathFromUri(uri: Uri): String? {
        return try {
            val file = File(uri.path!!)
            if (file.exists()) {
                file.absolutePath
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}