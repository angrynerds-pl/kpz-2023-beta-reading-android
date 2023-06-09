package com.example.betareadingapp.domain.use_case.auth

import com.example.betareadingapp.domain.model.RegisterData
import com.example.betareadingapp.domain.model.User
import com.example.betareadingapp.domain.use_case.ValidationResult
import com.example.betareadingapp.domain.util.Resource
import com.example.betareadingapp.feature_text.data.repository.Repository
import com.example.betareadingapp.feature_text.domain.util.error.ExceptionHandler
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUser
@Inject constructor(
    private val exceptionHandler: ExceptionHandler,
    private val repository: Repository,
    private val validation: RegisterValidation
) {

    operator fun invoke(registerData: RegisterData): Flow<Resource<FirebaseUser>> = flow {
        emit(Resource.Loading())
        when (val validationResult = validation(registerData)) {
            is ValidationResult.Success -> {
                val user = repository.register(
                    registerData.email,
                    registerData.password,
                    User(
                        registerData.name,
                        registerData.surname,
                        registerData.email
                    )
                )
                emit(Resource.Success(user))   //kiedys data bedzie byc moze wykorzystane
            }

            is ValidationResult.Error -> {
                emit(Resource.Error(message = validationResult.message))
            }
        }
    }.catch(exceptionHandler::handle)
}