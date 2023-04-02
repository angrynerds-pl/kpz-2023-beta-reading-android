package com.example.betareadingapp.feature_text.domain.util

sealed class TextOrder(val orderType: OrderType) {
    class Date(orderType: OrderType): TextOrder(orderType)
}
