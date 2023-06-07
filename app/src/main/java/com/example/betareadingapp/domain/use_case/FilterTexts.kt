package com.example.betareadingapp.domain.use_case

import com.example.betareadingapp.domain.model.Text
import javax.inject.Inject

class FilterTexts
@Inject constructor(){

    operator fun invoke(list : List<Text>?, filter_start : String) : List<Text>{

        return list?.filter { it.title.startsWith(filter_start, ignoreCase = true)} ?: emptyList()
    }
}