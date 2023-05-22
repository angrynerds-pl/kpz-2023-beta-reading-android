package com.example.betareadingapp.feature_text.domain.util.networkState

import com.example.betareadingapp.feature_text.domain.model.User

data class UserState(
    val data: User? = null,
    val error: String = "",
    val isLoading: Boolean = false
)
