package com.example.betareadingapp.domain.use_case.log_user

import com.example.betareadingapp.domain.model.CommentUploadData
import com.example.betareadingapp.domain.use_case.ValidationResult
import com.example.betareadingapp.domain.util.Resource
import com.example.betareadingapp.feature_text.data.repository.Repository
import com.example.betareadingapp.feature_text.domain.util.error.ExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddComment
@Inject constructor(
    private val repository: Repository,
    private val exceptionHandler: ExceptionHandler,
    private val addCommentValidation: AddCommentValidation
) {
    operator fun invoke(commentUploadData: CommentUploadData): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        when (val validationResult = addCommentValidation(commentUploadData.content)) {

            is ValidationResult.Success -> {
                repository.uploadComment(commentUploadData.textId, commentUploadData.content)
                emit(Resource.Success(Unit))
            }

            is ValidationResult.Error -> {
                emit(Resource.Error(message = validationResult.message))
            }
        }
    }.catch(exceptionHandler::handle)
}