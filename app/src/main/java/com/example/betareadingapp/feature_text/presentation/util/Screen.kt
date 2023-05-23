package com.example.betareadingapp.feature_text.presentation.util

sealed class Screen (val route: String){
    object WriterMyTextsScreen: Screen("writer_my_texts_screen")
}
