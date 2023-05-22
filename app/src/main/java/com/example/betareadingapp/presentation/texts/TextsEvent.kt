package com.example.betareadingapp.presentation.texts

import com.example.betareadingapp.domain.model.Text
import com.example.betareadingapp.domain.util.TextOrder

sealed class TextsEvent{
    data class Order(val textOrder: TextOrder) : TextsEvent()  // na razie nieuzywane
    data class FilterTexts (val filter_string : String)  : TextsEvent()

    data class DeleteText(val text : Text): TextsEvent()
}
