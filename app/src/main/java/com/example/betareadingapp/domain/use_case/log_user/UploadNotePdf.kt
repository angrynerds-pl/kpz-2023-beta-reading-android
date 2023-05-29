package com.example.betareadingapp.domain.use_case.log_user

import com.example.betareadingapp.domain.model.PdfData
import com.example.betareadingapp.domain.use_case.ValidationResult
import com.example.betareadingapp.domain.util.Resource
import com.example.betareadingapp.feature_text.data.repository.Repository
import com.example.betareadingapp.feature_text.domain.util.error.ExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UploadNotePdf
@Inject constructor(
    private val exceptionHandler: ExceptionHandler,
    private val repository: Repository,
    private val validation: UploadNotePdfValidation
) {
    operator fun invoke(pdfData: PdfData): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        when (val validationResult = validation(pdfData)) {
            is ValidationResult.Success -> {
                repository.uploadPdfToFirebaseStorage1(
                    pdfData.uri!!,
                    pdfData.fileName,
                    pdfData.content,
                    pdfData.title
                )
                emit(Resource.Success(Unit))
            }

            is ValidationResult.Error -> {
                emit(Resource.Error(message = validationResult.message))
            }
        }
    }.catch(exceptionHandler::handle)
}