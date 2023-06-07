package com.example.betareadingapp.domain.use_case.auth

import com.example.betareadingapp.domain.model.LoginData
import com.example.betareadingapp.domain.util.Resource
import com.example.betareadingapp.feature_text.data.repository.Repository
import com.example.betareadingapp.feature_text.domain.util.error.ExceptionHandler
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class LoginUser
@Inject constructor(
    private val exceptionHandler: ExceptionHandler,
    private val repository: Repository
) {
    operator fun invoke(loginData: LoginData): Flow<Resource<FirebaseUser>> = flow {
        emit(Resource.Loading())

       val user = repository.login(loginData.login, loginData.password)
        emit(Resource.Success(user))
        
    }.catch(exceptionHandler::handle)
}