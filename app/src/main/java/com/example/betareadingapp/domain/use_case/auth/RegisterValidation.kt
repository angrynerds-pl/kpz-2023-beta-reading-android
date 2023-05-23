package com.example.betareadingapp.domain.use_case.auth

import com.example.betareadingapp.domain.model.RegisterData
import javax.inject.Inject

class RegisterValidation
@Inject constructor() {
    operator fun invoke(registerData: RegisterData): RegisterValidationResult {
        if (registerData.name.isEmpty() || registerData.surname.isEmpty()) {
            return RegisterValidationResult.Error(ERROR_EMPTY_NAME)
        }
        if (registerData.password.isEmpty() || registerData.repeatPassword.isEmpty()) {
            return RegisterValidationResult.Error(ERROR_EMPTY_PASSWORDS)
        }
        if (registerData.password != registerData.repeatPassword) {
            return RegisterValidationResult.Error(ERROR_DIFFERENT_PASSWORDS)
        }
        return RegisterValidationResult.Success
    }

    companion object {
        const val ERROR_EMPTY_NAME = "Given name or surname are empty"
        const val ERROR_EMPTY_PASSWORDS = "Given passwords are empty"
        const val ERROR_DIFFERENT_PASSWORDS = "Given passwords are different"
    }
}