package com.example.betareadingapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.betareadingapp.R
import com.example.betareadingapp.presentation.auth.LoginScreen
import com.example.betareadingapp.presentation.mytexts.MyTextsScreen
import com.example.betareadingapp.presentation.register.RegisterScreen
import com.example.betareadingapp.presentation.utill.Screen
import com.example.betareadingapp.feature_text.presentation.attachfile.AttachFileScreen
import com.example.betareadingapp.presentation.comments.CommentsScreen
import com.example.betareadingapp.presentation.recenttexts.RecentTextsScreen
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
                Surface(modifier = Modifier.fillMaxSize(), color = colorResource(R.color.background_color)) {

                    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
                        composable(Screen.RegisterScreen.route) {
                            RegisterScreen(navController)
                        }
                        composable(Screen.LoginScreen.route) {
                            LoginScreen(navController)
                        }
                        composable(Screen.MyTextsScreen.route) {
                            MyTextsScreen(navController)
                        }
                        composable(Screen.AttachFileScreen.route) {
                            AttachFileScreen(navController)
                        }
                        composable(
                            route = Screen.CommentsScreen.route +
                                    "?textId={textId}",
                            arguments = listOf(
                                navArgument(
                                    name = "textId"
                                ) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                            )
                        ) {
                            CommentsScreen(navController)
                        }
                        composable(Screen.RecentTextsScreen.route){
                            RecentTextsScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
