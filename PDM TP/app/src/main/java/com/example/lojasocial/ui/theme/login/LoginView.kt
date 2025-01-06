package com.example.lojasocial.ui.theme.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.lojasocial.ui.theme.LojaSocialTheme
import com.example.lojasocial.R

@Composable
fun LoginView(navController: NavController, modifier: Modifier = Modifier, onLoginSuccess: (Any) -> Unit = {}) {

    val viewModel : LoginViewModel = viewModel()
    val state by viewModel.state

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                modifier = Modifier
                    .width(300.dp)
                    .height(300.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = ""
            )

            TextField(value = state.email,
                onValueChange = viewModel::onEmailChange,
                label = { Text("email") },
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "") },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(value = state.password,
                onValueChange = viewModel::onPasswordChange,
                label = { Text(text = "palavra-passe") },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "") },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row{
                Button(onClick = { viewModel.login(onLoginSuccess = onLoginSuccess) }) { Text(text = "Login") }

                Spacer(modifier = Modifier.width(16.dp))

                Button(onClick = { navController.navigate("register") },)  { Text(text = "Registar") }
            }

            TextButton(onClick = { }) {
                Text("Não sabes a tua palavra-passe?")
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