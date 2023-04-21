package com.example.betareadingapp.feature_text.presentation.texts

import com.example.betareadingapp.feature_text.domain.model.Text
import com.example.betareadingapp.feature_text.domain.util.TextOrder

sealed class TextsEvent{
    data class Order(val textOrder: TextOrder) : TextsEvent()  // na razie nieuzywane
    data class FilterTexts (val filter_string : String)  : TextsEvent()

    data class DeleteText(val text : Text): TextsEvent()
}
