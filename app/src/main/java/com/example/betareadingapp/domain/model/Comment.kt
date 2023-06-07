package com.example.betareadingapp.domain.model

import com.google.firebase.Timestamp

data class Comment(
    val textId: String = "",
    val userId: String = "",
    val commentId : String = "",
    val timestamp: Timestamp? = null,
    val content: String = "",
    val author: String = ""
)
