package com.example.lojasocial.ui.theme.transacoes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.lojasocial.ui.theme.bars.TopBar
import com.example.lojasocial.ui.theme.usercontrol.TransactionTypeSelector

@Composable
fun AddTransactionView(navController: NavController = rememberNavController()) {
    val viewModel: AddTransactionViewModel = viewModel()
    val state by viewModel.state

    TopBar(title = "Adicionar transação", navController = navController)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        TextField(
            value = state.descricao,
            onValueChange = viewModel::onDescriptionChange,
            label = { Text("descrição") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.valor,
            onValueChange = viewModel::onAmountChange,
            label = { Text("valor") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Selecionador de tipo de transação
        TransactionTypeSelector(
            selectedType = state.tipo,
            onTypeChange = viewModel::onTypeChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
               viewModel.add(onSuccess = { navController.popBackStack() })
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
            enabled = !state.isLoading
        )
        {
            Text(if (state.isLoading) "a carregar..."  else "adicionar")
        }

        Spacer(modifier = Modifier.height(16.dp))
        state.errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

    }
}