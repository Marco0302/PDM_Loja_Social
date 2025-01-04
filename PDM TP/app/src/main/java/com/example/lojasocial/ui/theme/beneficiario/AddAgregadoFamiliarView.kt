package com.example.lojasocial.ui.theme.beneficiario

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import com.example.lojasocial.data.models.Beneficiario

@Composable
fun AddAgregadoFamiliarView(navController: NavController, beneficiarioId: String) {
    val viewModel: AddAgregadoFamiliarViewModel = viewModel()
    val state by viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.nome,
            onValueChange = viewModel::onNomeChange,
            label = { Text("nome") },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "") },
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
            value = state.parentesco,
            onValueChange = viewModel::onParentescoChange,
            label = { Text("parantesco") },
            leadingIcon = { Icon(Icons.Filled.AccountCircle, contentDescription = "") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(0.8f)
                .shadow(4.dp, shape = RoundedCornerShape(20.dp))
        )


        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
                if (state.nome.isNotEmpty() && state.parentesco.isNotEmpty())
                {
                    val success = viewModel.create(beneficiarioId)
                    if(success)
                        navController.popBackStack()
                }
                else
                {
                    state.errorMessage = "preencha todos os campos."
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
            enabled = !state.isLoading
        )
        {
            Text(if (state.isLoading) "carregando..." else "adicionar agregado familiar")
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