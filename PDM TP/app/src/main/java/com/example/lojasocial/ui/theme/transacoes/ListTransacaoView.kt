package com.example.lojasocial.ui.theme.transacoes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

            Button(onClick = {
                navController.navigate("addTransacao")
            },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            {
                Text(text = "Adicionar Transação")
            }

            LazyColumn(contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(
                    items = state.listTransaction
                ) { _, item ->
                    TransactionCard (
                        description = item.descricao,
                        amount = item.valor,
                        type = item.tipo,
                        date = item.data,
                    )
                }
            }
        }

        //FloatingActionButton(
        //   onClick = { navController.navigate("addTransacao") },
        //   containerColor = Color.Blue,
            //modifier = Modifier
            //  .align(Alignment.BottomEnd)
        //       .size(80.dp)
        //       .padding(20.dp)
                //)
        //{
        //    Icon(
        //        imageVector = Icons.Default.Add,
        //        contentDescription = "adicionar transação",
        //        tint = Color.White
        //    )
        //}
    }

    LaunchedEffect (key1 = Unit){
        viewModel.loadListTransaction()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShowBeneficiaryView(){
}