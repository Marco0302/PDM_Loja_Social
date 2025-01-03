package com.example.lojasocial.ui.theme.transacoes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.example.lojasocial.ui.theme.bars.TopBar
import com.example.lojasocial.ui.theme.usercontrol.TransactionCard

@Composable
fun ListTransactionView(navController: NavController) {
    val viewModel: ListTransactionViewModel = viewModel()
    val state by viewModel.state

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            TopBar(title = "transações monetárias", navController = navController)
            LazyColumn(contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(
                    items = state.listTransaction
                ) { _, item ->
                    TransactionCard (
                        description = item.description,
                        amount = item.amount,
                        type = item.type,
                        date = item.date,
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate("addTransactions") },
            containerColor = Color.Blue,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(80.dp)
                .padding(20.dp)
        )
        {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "adicionar transação",
                tint = Color.White
            )
        }
    }

    LaunchedEffect (key1 = Unit){
        viewModel.loadListTransaction()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShowBeneficiaryView(){
}