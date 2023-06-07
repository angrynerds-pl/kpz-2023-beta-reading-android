package com.example.betareadingapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.betareadingapp.R

@Composable
fun TopBarWithImage(
    title: String
) {
    TopAppBar(
        backgroundColor = colorResource(R.color.background_color),
        contentPadding = PaddingValues(10.dp),
        elevation = 0.dp
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
        ) {
            Text(
                title,
                color = MaterialTheme.colors.onPrimary,
                fontSize = 22.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.smalllogo),
                contentDescription = "Small Logo",
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}