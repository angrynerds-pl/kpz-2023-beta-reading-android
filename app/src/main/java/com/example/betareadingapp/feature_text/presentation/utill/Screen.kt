package com.example.betareadingapp.feature_text.presentation.utill

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object MyTextsScreen : Screen("my_text_screen")
    object AttachFileScreen : Screen("attach_file_screen")
    object RecentTextsScreen : Screen("recent_texts_screen")
}