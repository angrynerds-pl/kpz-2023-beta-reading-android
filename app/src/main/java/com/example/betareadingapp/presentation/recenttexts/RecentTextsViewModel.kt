package com.example.betareadingapp.presentation.recenttexts

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betareadingapp.domain.use_case.log_user.LogUserUseCases
import com.example.betareadingapp.domain.util.Resource
import com.example.betareadingapp.domain.util.networkState.MyTextsState
import com.example.betareadingapp.domain.util.networkState.RecentTextsState
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
    private val logUserUseCases: LogUserUseCases
) : ViewModel() {
    private val _recentTextsState = MutableStateFlow(RecentTextsState())

    val recentTextsState: StateFlow<RecentTextsState> = _recentTextsState

    val _search = mutableStateOf("")

    val search: State<String> = _search

    init {
        getUserTexts(_search.value)
  }

    fun setSearch(setString: String) {
        _search.value = setString
    }

    fun searchfilterTexts() {
        getUserTexts(_search.value)
    }

    fun getUserTexts(filterText : String) {
        logUserUseCases.getRecentTexts(filterText).onEach {
            _recentTextsState.value = when (it) {
                is Resource.Loading -> RecentTextsState(isLoading = true)
                is Resource.Error -> RecentTextsState(error = it.message ?: "")
                is Resource.Success -> RecentTextsState(data = it.data)
            }
        }.launchIn(viewModelScope)
    }
}