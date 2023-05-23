package com.example.betareadingapp.feature_text.presentation.mytexts.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.betareadingapp.feature_text.domain.model.Text
import com.example.betareadingapp.feature_text.presentation.utill.Screen

@Composable
fun TextItem(
    text: Text,
    navController: NavController
) {
    Box() {
        Card(
            elevation = 4.dp,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                  navController.navigate(Screen.CommentsScreen.route)
                }
        ) {
            Column(modifier = Modifier.padding(start = 2.dp)) {
                Text(text = text.title, style = MaterialTheme.typography.h6)
                Text(text = text.author, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = text.content.take(300), fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            }
        }
    }
}