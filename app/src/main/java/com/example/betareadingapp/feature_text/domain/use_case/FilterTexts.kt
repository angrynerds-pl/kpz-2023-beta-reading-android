package com.example.betareadingapp.feature_text.domain.use_case

import com.example.betareadingapp.feature_text.domain.model.Text

class FilterTexts {

    operator fun invoke(list : List<Text>, filter_start : String) : List<Text>{

        return list.filter { it.title.startsWith(filter_start, ignoreCase = true)}
    }
}