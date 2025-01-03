package com.example.lojasocial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.lojasocial.ui.theme.LojaSocialTheme
import com.example.lojasocial.ui.theme.home.HomeView
import com.example.lojasocial.ui.theme.login.LoginView
import com.example.lojasocial.ui.theme.register.RegisterView
import com.example.lojasocial.ui.theme.transacoes.AddTransactionView
import com.example.lojasocial.ui.theme.transacoes.ListTransactionView
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            LojaSocialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = "login"
                    )
                    {
                        composable("login") {
                            LoginView(navController, onLoginSuccess = { navController.navigate("home") })
                        }
                        composable("register"){
                            RegisterView(navController, onRegisterSuccess = { navController.navigate("login") })
                        }
                        composable("home") {
                            HomeView(navController)
                        }
                        composable("lisTransactions"){
                            ListTransactionView(navController)
                        }
                        composable("addTransactions"){
                            AddTransactionView(navController)
                        }

                    }
                }
            }

            LaunchedEffect(Unit) {

                val auth = Firebase.auth

                val currentUser = auth.currentUser
                if (currentUser != null) {
                    navController.navigate("home")
                }
            }

        }
    }
}