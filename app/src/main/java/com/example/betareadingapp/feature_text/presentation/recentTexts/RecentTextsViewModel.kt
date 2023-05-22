package com.example.betareadingapp.feature_text.presentation.recentTexts

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betareadingapp.feature_text.data.repository.AuthRepository
import com.example.betareadingapp.feature_text.domain.util.Resource
import com.example.betareadingapp.feature_text.domain.util.networkState.MyTextsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class RecentTextsViewModel
@Inject
constructor(
    private var authRepository: AuthRepository
) : ViewModel() {
    private val _allTexts = MutableStateFlow(MyTextsState())

    val allTexts: StateFlow<MyTextsState> = _allTexts

    val _search = mutableStateOf("")
    val search: State<String> = _search

    init {
        getAllUserTexts()
    }

    fun setSearch(setString: String) {
        _search.value = setString
    }

    fun  getAllUserTexts() {
        authRepository.getAllTexts().onEach {
            when (it) {
                is Resource.Loading -> {
                    _allTexts.value = MyTextsState(isLoading = true)
                }

                is Resource.Error -> {
                    _allTexts.value = MyTextsState(error = it.message ?: "")
                }

                is Resource.Success -> {
                    _allTexts.value = MyTextsState(data = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }
}
