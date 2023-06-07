package com.example.betareadingapp.presentation.mytexts.components

import android.graphics.Paint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.betareadingapp.domain.model.Text

@Composable
fun TextItem(
    text: Text,
    onShowPdf: () -> Unit,
    onShowComments: () -> Unit

) {
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
                    Text(
                        text = text.title, style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onSurface
                    )
                    Text(
                        text = text.author, fontSize = 14.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = text.content.take(300), fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                }
                Column(
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    IconButton(
                        onClick = onShowPdf
                    ) {
                        Icon(
                            imageVector = Icons.Default.PictureAsPdf,
                            contentDescription = "Open pdf",
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                    IconButton(
                        onClick = onShowComments
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Navigate to comments",
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                }
            }
        }
    }

}