package com.example.betareadingapp.presentation.comments

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
import com.example.betareadingapp.domain.model.Comment
import com.example.betareadingapp.domain.model.Text
import java.text.SimpleDateFormat
import java.util.*

@Composable

fun CommentItem(
    navController: NavController,
    comment: Comment
) {

    val format = SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault())
    val dateString = format.format(comment.timestamp?.toDate())
    Box() {
        Card(
            elevation = 4.dp,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(modifier = Modifier.padding(start = 2.dp, end = 52.dp)) {
                    Text(text = comment.author, style = MaterialTheme.typography.h6)
                    Text(text = dateString, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = comment.content.take(300), fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}