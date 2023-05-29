package com.example.betareadingapp.feature_text.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.betareadingapp.presentation.utill.Screen

@Composable
fun BottomBar(navController: NavController) {
    BottomAppBar(
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
            IconButton(onClick = { }) {
                Icon(Icons.Filled.Home, contentDescription = "")
            }
            IconButton(onClick = { }) {
                Icon(Icons.Filled.Chat, contentDescription = "")
            }
            IconButton(onClick = { navController.navigate(Screen.MyTextsScreen.route) }) {
                Icon(Icons.Default.Book, contentDescription = "")
            }
            IconButton(onClick = { }) {
                Icon(Icons.Filled.Person, contentDescription = "")
            }
        }
    }
}