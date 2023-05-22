package com.example.betareadingapp.domain.repository

import com.example.betareadingapp.domain.model.Text
import kotlinx.coroutines.flow.Flow

interface TextRepository {

    fun getTexts(): Flow<List<Text>>
    suspend fun getTextById(id: Int): Text?
    suspend fun insertText(text: Text)
    suspend fun deleteText(text: Text)

}