package com.example.betareadingapp.feature_text.domain.util.networkState
import com.example.betareadingapp.feature_text.domain.model.Comment

data class CommentState(
    val data: List<Comment>? = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)
