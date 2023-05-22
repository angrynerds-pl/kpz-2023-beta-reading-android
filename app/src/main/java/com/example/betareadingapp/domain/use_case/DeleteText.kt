package com.example.betareadingapp.domain.use_case

import com.example.betareadingapp.domain.model.Text
import com.example.betareadingapp.domain.repository.TextRepository

class DeleteText (
    private val repository: TextRepository
){
    suspend operator fun invoke(text: Text) {
        repository.deleteText(text)
    }
}