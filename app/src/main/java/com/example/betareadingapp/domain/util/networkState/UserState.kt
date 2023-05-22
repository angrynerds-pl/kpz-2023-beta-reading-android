package com.example.betareadingapp.domain.util.networkState

import com.example.betareadingapp.domain.model.User

data class UserState(
    val data: User? = null,
    val error: String = "",
    val isLoading: Boolean = false
)
