//package com.example.betareadingapp.feature_text.presentation.texts
//
//
//import android.graphics.Paint.Align
//import androidx.compose.animation.ExperimentalAnimationApi
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.FavoriteBorder
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material.icons.filled.ShoppingCart
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.compose.rememberNavController
//import com.ramcosta.composedestinations.annotation.Destination
//import com.ramcosta.composedestinations.navigation.DestinationsNavigator
//
//
//@Destination(start=true)
//@Composable
//fun TextsScreen(
////         navController: NavController,
////   navigator : DestinationsNavigator,
//   viewModel: TextsViewModel = hiltViewModel()
//)
//
//{
//    var text by remember { mutableStateOf("") }
//    val scaffoldState = rememberScaffoldState()
//
//
//
//    Scaffold(
//        backgroundColor = Color(0xFF43928A),
//        scaffoldState = scaffoldState
//    ){
//
//            Column(
//                modifier = Modifier.fillMaxSize(),
//                horizontalAlignment = Alignment.CenterHorizontally,
////        verticalArrangement =  Arrangement.Center
//            ) {
//                Text(
//                    "ELo",
//                    modifier = Modifier.padding(it)
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//                TextField(
//                    value = text, onValueChange = {
//                        text = it
//                    },
//                    placeholder = {
//                        Text("Search text")
//                    },
//                    trailingIcon = {
//                        IconButton(onClick = {
//
//                        })
//                        {
//                            Icon(
//                                imageVector = Icons.Filled.Search,
//                                contentDescription = null
//                            )
//                        }
//                    },
//                    shape = RoundedCornerShape(16.dp),
//                    isError = false,
//                    colors = TextFieldDefaults.textFieldColors(
//                        backgroundColor = Color.White
//                    ),
//                    modifier = Modifier
//                        .width(250.dp),
//                    maxLines = 1
//                )
//            }
//
//    }
//
////    Column(
////        modifier = Modifier.
////             fillMaxSize(),
////        horizontalAlignment = Alignment.CenterHorizontally,
////        verticalArrangement =  Arrangement.Center
////        ){
////
////        TextField(value = text, onValueChange = {
////            text = it
////        },
////            placeholder = {
////                Text("Search text")},
////            trailingIcon = {
////                IconButton(onClick = {
////
////                })
////                {
////                    Icon(
////                        imageVector = Icons.Filled.Search,
////                        contentDescription = null
////                    )
////                }
////            },
////            shape = RoundedCornerShape(16.dp),
////            isError = false,
////            colors = TextFieldDefaults.textFieldColors(
////                backgroundColor = Color.White),
////            modifier = Modifier
////                .width(250.dp),
////            maxLines = 1
////        )
////    }
//
//
//}
//
