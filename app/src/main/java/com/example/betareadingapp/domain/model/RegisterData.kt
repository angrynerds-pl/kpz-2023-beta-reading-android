package com.example.betareadingapp.domain.model

data class RegisterData(
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val repeatPassword: String
)
