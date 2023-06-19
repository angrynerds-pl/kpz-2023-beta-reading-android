package com.example.betareadingapp.storage

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import java.io.File
import javax.inject.Inject


class LocalStorageRepository @Inject constructor(
    private val context: Context
) {

    fun prepareDestinationInDownloads(name: String): Uri {
        val rootPath = File(context.getExternalFilesDir(null), name)
        return rootPath.toUri()
    }

    fun getUriForFile(originalPath: String): Uri =
        FileProvider.getUriForFile(
            context,
            "${context.applicationContext.packageName}.provider",
            File(originalPath)
        )
}
