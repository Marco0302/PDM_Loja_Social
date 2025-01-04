package com.example.lojasocial.ui.theme.transacoes

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.filled.Add
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import com.example.lojasocial.ui.theme.bars.TopBar
import com.example.lojasocial.ui.theme.usercontrol.TransactionCard

@Composable
fun ListTransactionView(navController: NavController) {
    val viewModel: ListTransactionViewModel = viewModel()
    val state by viewModel.state

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            TopBar(title = "Transações Monetárias", navController = navController)

            LazyColumn(contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(
                    items = state.list
                ) { _, item ->
                    TransactionCard (
                        descricao = item.descricao,
                        valor = item.valor,
                        tipo = item.tipo,
                        data = item.data,
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate("addTransacao") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color.DarkGray
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Adicionar Transação",
                tint = Color.White
            )
        }

    }

    LaunchedEffect (key1 = Unit){
        viewModel.loadListTransacao()
    }
}