package com.example.lojasocial.ui.theme.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.example.lojasocial.ui.theme.usercontrol.FuncionamentoCard

@Composable
fun ListFuncionamentoView(navController: NavController) {
    val viewModel: ListFuncionamentoViewModel = viewModel()
    val state by viewModel.state

    Box(modifier = Modifier.fillMaxSize()) {

        Column {
            TopBar(title = "Dias de Funcionamento", navController = navController)

            LazyColumn(contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(
                    items = state.list
                ) { _, item ->
                    FuncionamentoCard(
                        navController = navController,
                        id = item.id?: "",
                        data = item.data,
                        numeroMaxVoluntarios = item.numeroMaxVoluntarios.toString(),
                        vagasDisponiveis = item.vagasDisponiveis.toString(),
                        onClick = {
                            navController.navigate("listSolicitacaoPresenca/${item.id}")
                        }
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate("addHorariosFuncionamento") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color.DarkGray
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Adicionar Horário Funcionamento",
                tint = Color.White
            )
        }

    }

    LaunchedEffect (key1 = Unit){
        viewModel.loadListFuncionamento()
    }
}