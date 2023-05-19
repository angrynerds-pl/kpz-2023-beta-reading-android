package com.example.betareadingapp.feature_text.presentation.attachfile

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betareadingapp.feature_text.data.repository.AuthRepository
import com.example.betareadingapp.feature_text.domain.util.Resource
import com.example.betareadingapp.feature_text.domain.util.networkState.AuthState
import com.example.betareadingapp.feature_text.domain.util.networkState.PdfState
import com.example.betareadingapp.feature_text.domain.util.networkState.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttachFileViewModel
@Inject
constructor(
    private var authRepository: AuthRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _pdfState = MutableStateFlow(PdfState())
    val pdfState: StateFlow<PdfState> = _pdfState

    private val _titleField = mutableStateOf("")

    val titleField: State<String> = _titleField

    fun setTitleField(titleField: String) {
        _titleField.value = titleField
    }

    private val _content = mutableStateOf("")

    val content: State<String> = _content

    fun setContent(content: String) {
        _content.value = content
    }

    private val _uri: MutableState<Uri?> = mutableStateOf(null)
    fun setUri(newUri: Uri) {
        _uri.value = newUri
    }

    private val _fileName = mutableStateOf("")

    val fileName: State<String> = _fileName

    fun setFileName(fileName: String) {
        _fileName.value = fileName
    }

    //czarna magia
    fun getFileNameFromUri(uri: Uri): String? {
        var fileName: String? = null
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                fileName = cursor.getString(nameIndex)
            }
        }
        return fileName
    }

    fun setUriAndFilename(uri: Uri) {
        viewModelScope.launch {
            val fileName = getFileNameFromUri(uri) ?: "default"
            setFileName(fileName)
            setUri(uri)
        }
    }


    fun uploadPdfWithText() {
        if (_titleField.value.isEmpty() || _content.value.isEmpty()) {
            _pdfState.value = PdfState(error = "titleField or content is empty")
            return
        }
        if (_uri.value == null) {
            _pdfState.value = PdfState(error = "You didn't attach file")
            return
        }
        viewModelScope.launch {
            authRepository.getUserData().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _pdfState.value = PdfState(isLoading = true)
                    }

                    is Resource.Error -> {
                        _pdfState.value = PdfState(error = result.message ?: "")
                    }

                    is Resource.Success -> {
                        val author = "${result.data?.name} ${result.data?.surname} "
                        authRepository.uploadPdfToFirebaseStorage(
                            _uri.value!!,
                            _fileName.value,
                            author,
                            _titleField.value,
                            _content.value
                        ).onEach { uploadResult ->
                            when (uploadResult) {
                                is Resource.Loading -> {
                                    _pdfState.value = PdfState(isLoading = true)
                                }

                                is Resource.Success -> {
                                    _pdfState.value = PdfState(error = uploadResult.data ?: "") // taki pseudo Error
                                    _uri.value = null
                                    _titleField.value = ""
                                    _content.value = ""
                                }

                                is Resource.Error -> {
                                    _pdfState.value = PdfState(error = uploadResult.message ?: "")
                                }
                            }
                        }.launchIn(viewModelScope)
                    }
                }
            }
        }


    }
}
