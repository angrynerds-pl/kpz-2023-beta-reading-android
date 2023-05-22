package com.example.betareadingapp.domain.util

sealed class TextOrder(val orderType: OrderType) {
    class Date(orderType: OrderType): TextOrder(orderType)
}
