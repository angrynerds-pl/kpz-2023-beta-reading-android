package com.example.betareadingapp.domain.use_case.auth

import javax.inject.Inject

class AuthUseCases @Inject constructor(
    val loginUser: LoginUser
)