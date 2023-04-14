package com.example.betareadingapp.feature_text.presentation.texts

import com.example.betareadingapp.feature_text.domain.util.TextOrder

sealed class TextsEvent{
    data class Order(val textOrder: TextOrder) : TextsEvent()  // na razie nieuzywane q
    data class FilterTexts (val filter_string : String)  : TextsEvent()

}
