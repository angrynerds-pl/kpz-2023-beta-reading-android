package com.example.betareadingapp.feature_text.presentation.attachfile

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betareadingapp.domain.model.PdfData
import com.example.betareadingapp.domain.use_case.log_user.LogUserUseCases
import com.example.betareadingapp.domain.util.Resource
import com.example.betareadingapp.feature_text.domain.util.networkState.PdfState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AttachFileViewModel
@Inject
constructor(
    private val logUserUseCases: LogUserUseCases
) : ViewModel() {

    private val _pdfState = MutableStateFlow(PdfState())
    val pdfState: StateFlow<PdfState> = _pdfState

    private val _titleField = mutableStateOf("")
    val titleField: State<String> = _titleField

    private val _content = mutableStateOf("")
    val content: State<String> = _content

    private val _uri: MutableState<Uri?> = mutableStateOf(null)

    private val _fileName = mutableStateOf("")
    val filename: State<String> = _fileName

    fun setTitleField(titleField: String) {
        _titleField.value = titleField
    }

    fun setContent(content: String) {
        _content.value = content
    }

    fun setUriAndFilename(uri: Uri) {
        val fileName = logUserUseCases.getFileNameFromUri(uri)
        _fileName.value = fileName
        _uri.value = uri
    }

    fun uploadNotePdf() {
        val pdfdata = PdfData(
            uri = _uri.value,
            fileName = _fileName.value,
            title = _titleField.value,
            content = _content.value
        )
        logUserUseCases.uploadNotePdf(pdfdata).onEach {
            when (it) {
                is Resource.Loading -> {
                    _pdfState.value = PdfState(isLoading = true)
                }
                is Resource.Success -> {
                    _pdfState.value = PdfState()
                    _uri.value = null
                    _fileName.value = ""
                    _titleField.value = ""
                    _content.value = ""
                }
                is Resource.Error -> {
                    _pdfState.value = PdfState(error = it.message ?: "")
                }
            }
        }.launchIn(viewModelScope)
    }
}
