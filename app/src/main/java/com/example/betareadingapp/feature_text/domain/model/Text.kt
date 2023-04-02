package com.example.betareadingapp.feature_text.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Text(
    val title: String,
    val content: String,
    val timestamp: Long,
    @PrimaryKey val id: Int? = null
)