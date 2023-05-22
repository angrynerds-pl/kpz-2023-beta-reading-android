package com.example.betareadingapp.presentation.texts

import com.example.betareadingapp.domain.model.Text
import com.example.betareadingapp.domain.util.OrderType
import com.example.betareadingapp.domain.util.TextOrder

data class TextsState(
    val texts : List<Text> = emptyList(),
    val filterTexts : List<Text> = emptyList(),
    val textOrder : TextOrder = TextOrder.Date(OrderType.Descending)
)
