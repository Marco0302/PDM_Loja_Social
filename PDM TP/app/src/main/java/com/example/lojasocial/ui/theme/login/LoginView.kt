package com.example.lojasocial.ui.theme.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.lojasocial.ui.theme.LojaSocialTheme

@Composable
fun LoginView(navController: NavController, modifier: Modifier = Modifier, onLoginSuccess: () -> Unit = {}) {

    val viewModel : LoginViewModel = viewModel()
    val state by viewModel.state

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            TextField(value = state.email,
                onValueChange = viewModel::onEmailChange,
                label = { Text("email") },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(20.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(value = state.password,
                onValueChange = viewModel::onPasswordChange,
                label = { Text(text = "password") },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(20.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row{
                Button(onClick = { viewModel.login(onLoginSuccess = onLoginSuccess) }) { Text(text = "Login") }

                Spacer(modifier = Modifier.width(16.dp))

                Button(onClick = { navController.navigate("register") },)  { Text(text = "Registar") }
            }

            Spacer(modifier = Modifier.height(16.dp))
            state.errorMessage?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    LojaSocialTheme() {
        LoginView(navController = rememberNavController())
    }
}