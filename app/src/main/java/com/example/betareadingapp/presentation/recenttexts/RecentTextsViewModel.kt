package com.example.betareadingapp.presentation.recenttexts

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewModelScope
import com.example.betareadingapp.domain.use_case.log_user.LogUserUseCases
import com.example.betareadingapp.domain.util.Resource
import com.example.betareadingapp.domain.util.networkState.RecentTextsState
import com.example.betareadingapp.feature_text.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch


@HiltViewModel
class RecentTextsViewModel
@Inject
constructor(
    private val logUserUseCases: LogUserUseCases,
) : ViewModel() {
    private val _recentTextsState = MutableStateFlow(RecentTextsState())

    val recentTextsState: StateFlow<RecentTextsState> = _recentTextsState

    private val _search = mutableStateOf("")

    val search: State<String> = _search

    private val searchInputFlow = MutableSharedFlow<String>()

    private val _pdfUriState = MutableStateFlow<Uri?>(null)
    val pdfUriState: StateFlow<Uri?> = _pdfUriState

    val errorMessages = MutableSharedFlow<String>()

    init {
        getUserTexts(_search.value)
        searchInputFlow
            .debounce(500)    //obsluguje maksymalnie jedna wartosc co 0.5s
            .onEach { searchText ->     // kolektor ktory otrzymuje emit  searchInputFlow.emit(setString)
                getUserTexts(searchText)
            }
            .launchIn(viewModelScope)
    }


    fun downloadPdf(url: String) {
        logUserUseCases.downLoadPdf(url).onEach {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Error -> {
                    Log.d("DowloadPdf", "${it.message}")
                    errorMessages.emit("Error: ${it.message}")
                }
                is Resource.Success -> _pdfUriState.value = it.data     // do zrobienia, na razie nie mam pojecia jak poradzic sobie z uprawnieniami
            }

        }.launchIn(viewModelScope)
    }

    fun setSearch(setString: String) {
        _search.value = setString
        viewModelScope.launch {
            searchInputFlow.emit(setString)
        }
    }

    fun getUserTexts(filterText: String) {
        logUserUseCases.getRecentTexts(filterText).onEach {
            _recentTextsState.value = when (it) {
                is Resource.Loading -> RecentTextsState(isLoading = true)
                is Resource.Error -> RecentTextsState(error = it.message ?: "")
                is Resource.Success -> RecentTextsState(data = it.data)
            }
        }.launchIn(viewModelScope)
    }

}