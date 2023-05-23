package com.example.betareadingapp.feature_text.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.betareadingapp.feature_text.presentation.attachfile.AttachFileScreen
import com.example.betareadingapp.feature_text.presentation.auth.LoginScreen
import com.example.betareadingapp.feature_text.presentation.mytexts.CommentsScreen
import com.example.betareadingapp.feature_text.presentation.mytexts.MyTextsScreen
import com.example.betareadingapp.feature_text.presentation.recentTexts.RecentTextsScreen
import com.example.betareadingapp.feature_text.presentation.register.RegisterScreen
import com.example.betareadingapp.feature_text.presentation.utill.Screen
import com.example.betareadingapp.ui.theme.BetaReadingAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BetaReadingAppTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF43928A)) {

                    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
                        composable(Screen.RegisterScreen.route) {
                            RegisterScreen(navController)
                        }
                        composable(Screen.LoginScreen.route){
                            LoginScreen(navController)
                        }
                        composable(Screen.MyTextsScreen.route){
                            MyTextsScreen(navController)
                        }
                        composable(Screen.AttachFileScreen.route){
                            AttachFileScreen(navController)
                        }
                        composable(Screen.RecentTextsScreen.route){
                            RecentTextsScreen(navController)
                        }
                        composable(Screen.CommentsScreen.route){
                             CommentsScreen(navController)
                        }
                }
//                    AttachFileScreen(navController)

                }
            }
        }
    }
}