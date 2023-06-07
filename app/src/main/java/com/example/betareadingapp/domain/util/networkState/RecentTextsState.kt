package com.example.betareadingapp.domain.util.networkState

import com.example.betareadingapp.domain.model.Text

data class RecentTextsState(
    val data : List<Text>? = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)

