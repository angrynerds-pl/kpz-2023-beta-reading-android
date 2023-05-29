package com.example.betareadingapp.domain.use_case.auth

import com.example.betareadingapp.domain.model.RegisterData
import com.example.betareadingapp.domain.use_case.ValidationResult
import javax.inject.Inject

class RegisterValidation
@Inject constructor() {
    operator fun invoke(registerData: RegisterData): ValidationResult {
        if (registerData.name.isEmpty() || registerData.surname.isEmpty()) {
            return ValidationResult.Error(ERROR_EMPTY_NAME)
        }
        if (registerData.password.isEmpty() || registerData.repeatPassword.isEmpty()) {
            return ValidationResult.Error(ERROR_EMPTY_PASSWORDS)
        }
        if (registerData.password != registerData.repeatPassword) {
            return ValidationResult.Error(ERROR_DIFFERENT_PASSWORDS)
        }
        return ValidationResult.Success
    }

    companion object {
        const val ERROR_EMPTY_NAME = "Given name or surname are empty"
        const val ERROR_EMPTY_PASSWORDS = "Given passwords are empty"
        const val ERROR_DIFFERENT_PASSWORDS = "Given passwords are different"
    }
}