package com.example.betareadingapp.domain.use_case.log_user

import android.net.Uri
import com.example.betareadingapp.domain.model.PdfData
import com.example.betareadingapp.domain.use_case.ValidationResult
import javax.inject.Inject

class UploadNotePdfValidation
@Inject constructor() {

    operator fun invoke(pdfData: PdfData): ValidationResult {
        if (pdfData.uri == null)
            return ValidationResult.Error(empty_uri)

        if (pdfData.title.isEmpty())
            return ValidationResult.Error(empty_title)

        if (pdfData.content.isEmpty())
            return ValidationResult.Error(empty_content)

        return ValidationResult.Success
    }

    companion object {
        const val empty_uri = "Firstly attach file"
        const val empty_title = "Title is empty"
        const val empty_content = "Content is empty"

    }
}