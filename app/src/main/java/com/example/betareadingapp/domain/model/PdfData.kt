package com.example.betareadingapp.domain.model

import android.net.Uri

data class PdfData(
    val uri : Uri? = null,
    val fileName : String = "default",
    val title : String = "",
    val content : String = "",
)
