package com.example.betareadingapp.feature_text.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp

data class Text(
    val userId : String = "",
    val autor : String = "",
    val title: String = "",
    val content: String = "",
    val file : String = "",
    val timestamp: Timestamp? = null,
)