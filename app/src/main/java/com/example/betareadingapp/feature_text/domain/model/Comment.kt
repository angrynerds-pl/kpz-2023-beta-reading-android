package com.example.betareadingapp.feature_text.domain.model

import com.google.firebase.Timestamp

data class Comment(
    val userId : String = "",
    val author : String = "",
    val content: String = "",
    val textId : String = "",
    val timestamp: Timestamp? = null,
)
