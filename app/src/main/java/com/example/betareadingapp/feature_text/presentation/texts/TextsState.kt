package com.example.betareadingapp.feature_text.presentation.texts

import com.example.betareadingapp.feature_text.domain.model.Text
import com.example.betareadingapp.feature_text.domain.util.OrderType
import com.example.betareadingapp.feature_text.domain.util.TextOrder

data class TextsState(
    val texts : List<Text> = emptyList(),
    val filterTexts : List<Text> = emptyList(),
    val textOrder : TextOrder = TextOrder.Date(OrderType.Descending)
)
