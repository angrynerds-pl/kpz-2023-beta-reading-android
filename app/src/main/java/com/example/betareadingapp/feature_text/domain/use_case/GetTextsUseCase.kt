package com.example.betareadingapp.feature_text.domain.use_case

import com.example.betareadingapp.feature_text.domain.model.Text
import com.example.betareadingapp.feature_text.domain.repository.TextRepository
import com.example.betareadingapp.feature_text.domain.util.OrderType
import com.example.betareadingapp.feature_text.domain.util.TextOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTextsUseCase(
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