package com.example.betareadingapp.feature_text.domain.use_case

import com.example.betareadingapp.feature_text.domain.model.Text
import com.example.betareadingapp.feature_text.domain.repository.TextRepository

class DeleteText (
    private val repository: TextRepository
){
    suspend operator fun invoke(text: Text) {
        repository.deleteText(text)
    }
}