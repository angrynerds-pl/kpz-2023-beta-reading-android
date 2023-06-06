package com.example.betareadingapp.domain.use_case.log_user

import com.example.betareadingapp.domain.model.User
import com.example.betareadingapp.domain.util.Resource
import com.example.betareadingapp.feature_text.data.repository.Repository
import com.example.betareadingapp.feature_text.domain.util.error.ExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserData
@Inject constructor(
    private val repository: Repository,
    private val exceptionHandler: ExceptionHandler
){
    operator fun invoke() : Flow<Resource<User>> = flow {
        emit(Resource.Loading())

        val user = repository.getUserData()
        emit(Resource.Success(user))

    }.catch(exceptionHandler::handle)
}