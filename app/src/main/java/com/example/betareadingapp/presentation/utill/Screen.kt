package com.example.betareadingapp.presentation.utill

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object MyTextsScreen : Screen("my_text_screen")
    object AttachFileScreen : Screen("attach_file_screen")
    object CommentsScreen : Screen("comments_screen")
    object RecentTextsScreen : Screen("recent_texts_screen")
    object ProfileScreen : Screen("profile_screen")
}