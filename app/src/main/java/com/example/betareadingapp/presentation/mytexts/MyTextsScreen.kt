package com.example.betareadingapp.presentation.mytexts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.betareadingapp.R
import com.example.betareadingapp.presentation.utill.Screen
import com.example.betareadingapp.presentation.mytexts.components.TextItem
import com.example.betareadingapp.feature_text.presentation.BottomBar

@Composable
fun MyTextsScreen(
    navController: NavController,
    viewModel: MyTextsViewModel = hiltViewModel()
) {

    val myTexts = viewModel.myTextsState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = colorResource(R.color.background_color),
                contentPadding = PaddingValues(10.dp),
                elevation = 0.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        stringResource(R.string.my_texts),
                        color = Color.White,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Row {
                        Text(
                            stringResource(R.string.add_text),
                            fontSize = 22.sp,
                            color = Color.White,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                        IconButton(onClick = { navController.navigate(Screen.AttachFileScreen.route) }) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Add",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                    Image(
                        painter = painterResource(id = R.drawable.smalllogo),
                        contentDescription = "Small Logo",
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
        },
        bottomBar = {
            BottomBar(navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(colorResource(R.color.background_color))
                .padding(bottom = paddingValues.calculateBottomPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(25.dp))
            OutlinedTextField(
                value = viewModel.search.value,
                onValueChange = {
                    viewModel.setSearch(it)
                    viewModel.filterTexts()
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .clip(RoundedCornerShape(50.dp)),
                placeholder = { Text(stringResource(R.string.search)) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.surface,
                    unfocusedBorderColor = MaterialTheme.colors.surface,
                    backgroundColor = MaterialTheme.colors.surface,
                    textColor = MaterialTheme.colors.onSurface,
                    placeholderColor = MaterialTheme.colors.onSurface
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colors.onSurface
                    )
                }
            )
            Spacer(modifier = Modifier.height(25.dp))

            if (myTexts.value.error.isNotEmpty())
                Text(myTexts.value.error)
            if (myTexts.value.isLoading) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    CircularProgressIndicator(
                        color = Color.White
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(myTexts.value.filterData ?: emptyList()) { text ->
                    TextItem(text, {
                        navController.navigate(
                            Screen.CommentsScreen.route +
                                    "?textId=${text.textId}"
                        )
                    }, {
                        navController.navigate(
                            Screen.CommentsScreen.route +
                                    "?textId=${text.textId}"
                        )
                    })
                }
            }

        }
    }
}