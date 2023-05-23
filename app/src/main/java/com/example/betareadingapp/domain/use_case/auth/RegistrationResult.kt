package com.example.betareadingapp.domain.use_case.auth

import com.example.betareadingapp.domain.model.RegisterData

sealed class RegisterValidationResult {
    object Success : RegisterValidationResult()
    data class Error(val message: String) : RegisterValidationResult()
}