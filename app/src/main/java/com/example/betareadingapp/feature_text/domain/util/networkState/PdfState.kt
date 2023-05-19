package com.example.betareadingapp.feature_text.domain.util.networkState

import com.example.betareadingapp.feature_text.domain.model.User

data class PdfState(
    val data: String = "",
    val error: String = "",
    val isLoading: Boolean = false
)
