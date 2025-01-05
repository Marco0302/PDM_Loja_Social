package com.example.lojasocial.ui.theme.pedidos

import androidx.compose.material.icons.filled.Add
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
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
import com.example.lojasocial.ui.theme.usercontrol.PedidoCard

@Composable
fun ListPedidoView(navController: NavController) {
    val viewModel: ListPedidoViewModel = viewModel()
    val state by viewModel.state

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            TopBar(title = "Pedidos Especiais", navController = navController)

            LazyColumn(contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(
                    items = state.list
                ) { _, item ->
                    PedidoCard(
                        navController,
                        id = item.id,
                        descricao = item.descricao,
                        estado = item.estado,
                        data = item.data
                    )
                }
            }
        }
    }

    LaunchedEffect (key1 = Unit){
        viewModel.loadListPedido()
    }
}