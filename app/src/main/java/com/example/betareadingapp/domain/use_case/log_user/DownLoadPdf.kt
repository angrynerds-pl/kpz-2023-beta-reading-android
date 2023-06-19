package com.example.betareadingapp.domain.use_case.log_user

import android.net.Uri
import com.example.betareadingapp.domain.util.Resource
import com.example.betareadingapp.feature_text.data.repository.Repository
import com.example.betareadingapp.feature_text.domain.util.error.ExceptionHandler
import com.example.betareadingapp.storage.LocalStorageRepository
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject

class DownLoadPdf
@Inject constructor(
    private val localStorage: LocalStorageRepository,
    private val exceptionHandler: ExceptionHandler
) {
    operator fun invoke(pdfUrl: String): Flow<Resource<Uri>> = flow {
        emit(Resource.Loading())

        val storageReference = Firebase.storage.getReferenceFromUrl(pdfUrl)
        val fileName = storageReference.name.substringAfter("_")

        val fileUri = localStorage.prepareDestinationInDownloads(fileName)
        fileUri.path
            ?.run { File(this) }
            ?.let(storageReference::getFile)
            ?.await()

        val finalUri = fileUri.path?.run { localStorage.getUriForFile(this) }

        if (finalUri == null) {
            emit(Resource.Error<Uri>(null, message = "Save error"))
        } else {
            emit(Resource.Success(finalUri))
        }
    }.catch(exceptionHandler::handle)
}