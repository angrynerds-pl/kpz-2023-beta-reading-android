package com.example.betareadingapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp

data class Text(
    val userId : String = "",
    val author : String = "",
    val title: String = "",
    val content: String = "",
    val file : String = "",
    val timestamp: Timestamp? = null,
)