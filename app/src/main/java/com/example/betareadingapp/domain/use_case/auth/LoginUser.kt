package com.example.betareadingapp.domain.use_case.auth

import com.example.betareadingapp.domain.util.Resource
import com.example.betareadingapp.feature_text.data.repository.AuthRepository
import com.example.betareadingapp.feature_text.domain.util.error.ExceptionHandler
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject


class LoginUser
@Inject constructor(
    private val exceptionHandler: ExceptionHandler,
    private val authRepository: AuthRepository
) {
    operator fun invoke(authdata: AuthData): Flow<Resource<FirebaseUser>> = flow {
        emit(Resource.Loading())

       val user = authRepository.login(authdata.login, authdata.password)
        emit(Resource.Success(user))
        
    }.catch(exceptionHandler::handle)
}