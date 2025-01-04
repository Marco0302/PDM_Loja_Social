package com.example.lojasocial.ui.theme.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.lojasocial.ui.theme.LojaSocialTheme
import com.example.lojasocial.ui.theme.login.LoginViewModel

@Composable
fun HomeView(navController: NavController, modifier: Modifier = Modifier){
    val viewModelLogin = LoginViewModel()

    Box(modifier = modifier.fillMaxSize() ){
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Button(onClick = {
                navController.navigate("listBeneficiario")
            }) {
                Text(text = "beneficiários")
            }

            Button(onClick = {
                navController.navigate("lisTransactions")
            }) {
                Text(text = "transações monetárias")
            }

            Button(onClick = {
                navController.navigate("updateHorariosFuncionamento")
            }) {
                Text(text = "horários de funcionamento")
            }

            Button(onClick = {
                navController.navigate("showLists")
            }) {
                Text(text = "relatórios estatísticos")
            }

            Button(onClick = { viewModelLogin.logout(
                onLogoutSuccess = { navController.navigate("login") }) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
                Text("logout")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListTypesViewPreview() {
    LojaSocialTheme() {
        HomeView(navController = rememberNavController())
    }
}