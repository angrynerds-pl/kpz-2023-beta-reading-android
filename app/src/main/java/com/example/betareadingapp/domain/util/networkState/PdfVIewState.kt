package com.example.betareadingapp.domain.util.networkState

import android.net.Uri
import com.example.betareadingapp.domain.model.Text

data class PdfVIewState(
    val uri : Uri? = null,
    val error: String = "",
    val isLoading: Boolean = false
)
