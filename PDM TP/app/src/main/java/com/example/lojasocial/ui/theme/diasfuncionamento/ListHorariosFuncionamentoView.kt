package com.example.lojasocial.ui.theme.diasfuncionamento

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.lojasocial.ui.theme.usercontrol.HorarioFuncionamentoCard

@Composable
fun ListHorariosFuncionamentoView(navController: NavController) {
    val viewModel: ListHorarioFuncionamentoViewModel = viewModel()
    val state by viewModel.state

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            TopBar(title = "Dias de Funcionamento", navController = navController)

            LazyColumn(contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(
                    items = state.list
                ) { _, item ->
                    HorarioFuncionamentoCard(
                        id = item.id ?: "",
                        data = item.data,
                        numeroMaxVoluntarios = item.numeroMaxVoluntarios.toString(),
                        vagasDisponiveis = item.vagasDisponiveis.toString(),
                        onClick = {
                            navController.navigate("listPessoaisSolicitacaoPresenca/${item.id}")
                        }
                    )
                }
            }
        }
    }

    LaunchedEffect (key1 = Unit){
        viewModel.loadListHorariosFuncionamento()
    }
}