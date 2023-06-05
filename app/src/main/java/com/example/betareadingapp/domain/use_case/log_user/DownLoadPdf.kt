package com.example.betareadingapp.domain.use_case.log_user

import android.net.Uri
import com.example.betareadingapp.domain.util.Resource
import com.example.betareadingapp.feature_text.data.repository.Repository
import com.example.betareadingapp.feature_text.domain.util.error.ExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DownLoadPdf
@Inject constructor(
    private val repository: Repository,
    private val exceptionHandler: ExceptionHandler
){
    operator fun invoke(pdfUrl: String) : Flow<Resource<Uri>> = flow {
        emit(Resource.Loading())
        val pdfUri = repository.downloadPdf(pdfUrl)
        emit(Resource.Success(pdfUri))

    }.catch(exceptionHandler::handle)
}