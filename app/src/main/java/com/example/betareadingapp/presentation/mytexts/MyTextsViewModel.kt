package com.example.betareadingapp.presentation.mytexts

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betareadingapp.domain.use_case.log_user.LogUserUseCases
import com.example.betareadingapp.domain.util.Resource
import com.example.betareadingapp.domain.util.networkState.MyTextsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MyTextsViewModel
@Inject
constructor(
    private val logUserUseCases: LogUserUseCases
) : ViewModel() {
    private val _myTextsState = MutableStateFlow(MyTextsState())

    val myTextsState: StateFlow<MyTextsState> = _myTextsState

    val _search = mutableStateOf("")
    val search: State<String> = _search

    init {
        getUserTexts()
    }

    fun setSearch(setString: String) {
        _search.value = setString
    }

    fun filterTexts() {
        if (_search.value.isEmpty())
            _myTextsState.value = _myTextsState.value.copy(filterData = _myTextsState.value.data)
        else
            _myTextsState.value = _myTextsState.value.copy(
                filterData = logUserUseCases.filterTexts(
                    _myTextsState.value.data,
                    _search.value
                )
            )
    }


    fun getUserTexts() {
        logUserUseCases.getMyTexts.invoke().onEach {
            _myTextsState.value = when (it) {
                is Resource.Loading -> MyTextsState(isLoading = true)
                is Resource.Error -> MyTextsState(error = it.message ?: "")
                is Resource.Success -> MyTextsState(data = it.data, filterData = it.data)
            }
        }.launchIn(viewModelScope)
    }
}
