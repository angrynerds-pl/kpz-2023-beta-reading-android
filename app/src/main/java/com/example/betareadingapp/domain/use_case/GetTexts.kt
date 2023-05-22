package com.example.betareadingapp.domain.use_case

import com.example.betareadingapp.domain.model.Text
import com.example.betareadingapp.domain.repository.TextRepository
import com.example.betareadingapp.domain.util.OrderType
import com.example.betareadingapp.domain.util.TextOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTexts(
    private val repository: TextRepository
) {
    operator fun invoke(
        textOrder: TextOrder = TextOrder.Date(OrderType.Descending)
    ): Flow<List<Text>> {
        return repository.getTexts().map { texts ->
            when(textOrder.orderType){
                is OrderType.Descending -> {
                    when (textOrder){
                        is TextOrder.Date -> texts.sortedBy{it.timestamp}
                    }
                }
            }
        }
    }
}