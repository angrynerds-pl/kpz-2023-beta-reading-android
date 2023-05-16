package com.example.betareadingapp.feature_text.domain.util.networkState

import com.example.betareadingapp.feature_text.domain.model.Text

data class MyTextsState(
    val data : List<Text>? = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)
