package com.example.betareadingapp.domain.use_case.log_user

import com.example.betareadingapp.domain.model.PdfData
import com.example.betareadingapp.domain.use_case.ValidationResult
import javax.inject.Inject

class AddCommentValidation
@Inject
constructor() {

    operator fun invoke(content: String): ValidationResult {
        if (content.isEmpty())
            return ValidationResult.Error(empty_content)

        return ValidationResult.Success
    }

    companion object {
        const val empty_content = "Empty content"


    }
}