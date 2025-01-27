package com.example.lojasocial.ui.theme.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.text.style.TextAlign

@Composable
fun RegisterView(navController: NavController, onRegisterSuccess: () -> Unit) {
    val viewModel: RegisterViewModel = viewModel()
    val state by viewModel.state
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            value = state.name,
            onValueChange = viewModel::onNameChange,
            label = { Text("nome") },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Nome Icon") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(0.8f)
                .shadow(4.dp, shape = RoundedCornerShape(20.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("email") },
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email Icon") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(0.8f)
                .shadow(4.dp, shape = RoundedCornerShape(20.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.telefone,
            onValueChange = viewModel::onTelefoneChange,
            label = { Text("telefone") },
            leadingIcon = { Icon(Icons.Filled.Phone, contentDescription = "Phone Icon") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(0.8f)
                .shadow(4.dp, shape = RoundedCornerShape(20.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("password") },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password Icon") },
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Lock
                else Icons.Filled.Lock

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = "Password Visibility Icon")
                }
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(20.dp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(0.8f)
                .shadow(4.dp, shape = RoundedCornerShape(20.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.confirmPassword,
            onValueChange = viewModel::onConfirmPasswordChange,
            label = { Text("confirmar password") },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "confirmar Password Icon") },
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Lock
                else Icons.Filled.Lock

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = "password Visibility Icon")
                }
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(20.dp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(0.8f)
                .shadow(4.dp, shape = RoundedCornerShape(20.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        // -------------------------------------------------------
        // Secção para escolher o role (RadioButtons)
        // -------------------------------------------------------
        val roles = listOf("admin", "membro da junta", "voluntário")

        Text(
            text = "Tipo de utilizador:",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
                .padding(start = 35.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 35.dp)
        ){

            roles.forEach { roleOption ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    RadioButton(
                        selected = (roleOption == state.role),
                        onClick = {
                            viewModel.onRoleChange(roleOption)
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = roleOption)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // -------------------------------------------------------

        Button(
            onClick = {
                viewModel.register(onRegisterSuccess = onRegisterSuccess, onRegisterFailure = { message ->
                    state.errorMessage = message
                })
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(0.8f),
            enabled = state.name.isNotEmpty() &&
                    state.email.isNotEmpty() &&
                    state.password.isNotEmpty() &&
                    state.password == state.confirmPassword &&
                    !state.isLoading
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(text = "registar")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            navController.navigate("login")
        },
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(0.8f),) {
            Text("voltar")
        }

        if (state.errorMessage != null) {
            Text(
                text = state.errorMessage ?: "",
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterView() {
    MaterialTheme {
        RegisterView(
            navController = rememberNavController(),
            onRegisterSuccess = {}
        )
    }
}
