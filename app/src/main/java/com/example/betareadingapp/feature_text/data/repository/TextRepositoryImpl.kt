package com.example.betareadingapp.feature_text.data.repository

import com.example.betareadinapp.feature_text.data.data_source.TextDao
import com.example.betareadingapp.feature_text.domain.model.Text
import com.example.betareadingapp.feature_text.domain.repository.TextRepository
import kotlinx.coroutines.flow.Flow

class TextRepositoryImpl(
    private val dao: TextDao

): TextRepository {
    override fun getTexts(): Flow<List<Text>> {
        return dao.getTexts()
    }

    override suspend fun getTextById(id: Int): Text? {
        return dao.getTextById(id)
    }

    override suspend fun insertText(text: Text) {
        return dao.insertText(text)
    }

    override suspend fun deleteText(text: Text) {
        return dao.deleteText(text)
    }
}