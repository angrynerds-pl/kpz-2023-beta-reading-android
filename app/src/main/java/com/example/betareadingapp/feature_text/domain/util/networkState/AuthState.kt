package com.example.betareadingapp.feature_text.domain.util.networkState

import com.google.firebase.auth.FirebaseUser

data class AuthState(
    val data: FirebaseUser? = null,
    val error: String = "",
    val isLoading: Boolean = false
)
