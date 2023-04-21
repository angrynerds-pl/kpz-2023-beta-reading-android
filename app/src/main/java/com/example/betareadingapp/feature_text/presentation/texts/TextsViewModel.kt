package com.example.betareadingapp.feature_text.presentation.texts

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betareadingapp.feature_text.domain.use_case.TextUseCases
import com.example.betareadingapp.feature_text.domain.util.OrderType
import com.example.betareadingapp.feature_text.domain.util.TextOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TextsViewModel @Inject constructor(

    private val textUseCases: TextUseCases
) : ViewModel() {

    private val _state = mutableStateOf(TextsState())
    val state : State<TextsState> = _state

    private var getNotesJob: Job? = null

    init {
        getTexts(TextOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: TextsEvent) {
        when(event){
             is TextsEvent.Order -> {
                TODO()
            }
            is TextsEvent.DeleteText -> {
                viewModelScope.launch {
                    textUseCases.deleteText(event.text)
                    //restoring recently deleted can be added
                }
            }

            is TextsEvent.FilterTexts -> {

                if(event.filter_string.isEmpty()){

                    getTexts(state.value.textOrder)
                }
                else{
                    _state.value = state.value.copy(
                        filterTexts = textUseCases.filterTexts( state.value.texts, event.filter_string)
                    )
                }

            }
        }
    }
    private fun getTexts(textOrder : TextOrder){
        getNotesJob?.cancel()
        getNotesJob = textUseCases.getTexts(textOrder)
            .onEach { texts ->
                _state.value = state.value.copy(
                    texts = texts,
                    textOrder = textOrder,
                    filterTexts = texts
                )
            }
            .launchIn(viewModelScope)
    }

}