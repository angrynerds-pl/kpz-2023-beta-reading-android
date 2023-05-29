package com.example.betareadingapp.domain.use_case.log_user

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetFileNameFromUri
@Inject constructor(
    @ApplicationContext private val context: Context
) {

    operator fun invoke(uri: Uri): String{
            var fileName: String? = null
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    fileName = cursor.getString(nameIndex)
                }
            }
            return fileName ?: "default"


    }
}