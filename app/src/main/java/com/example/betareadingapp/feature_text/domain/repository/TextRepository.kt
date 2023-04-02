package com.example.betareadingapp.feature_text.domain.repository

import com.example.betareadingapp.feature_text.domain.model.Text
import kotlinx.coroutines.flow.Flow

interface TextRepository {

    fun getTexts(): Flow<List<Text>>
    suspend fun getTextById(id: Int): Text?
    suspend fun insertText(text: Text)
    suspend fun deleteText(text: Text)

}