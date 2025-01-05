package com.example.lojasocial.ui.theme.diasfuncionamento

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lojasocial.data.models.CandidaturaHorarioFuncionamento
import com.example.lojasocial.ui.theme.bars.TopBar
import com.example.lojasocial.data.repository.CandidaturaHorarioFuncionamentoRepository
import com.example.lojasocial.data.repository.HorariosRepository
import com.example.lojasocial.ui.theme.usercontrol.AgregadoFamiliarCard
import com.example.lojasocial.ui.theme.usercontrol.PessoaisSolicitacoesPresencaCard

@Composable
fun ListPessoaisSolicitacoesPresencaView(navController: NavController, diaFuncionamentoId: String) {
    var item by remember { mutableStateOf<List<CandidaturaHorarioFuncionamento>?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(diaFuncionamentoId) {
        CandidaturaHorarioFuncionamentoRepository.getSolicitacoesPessoais(
            listId = diaFuncionamentoId,
            onSuccess = { fetchedItems ->
                item = fetchedItems
                isLoading = false
            },
            onFailure = {
                isLoading = false
            }
        )
    }

    TopBar("Solicitações de Presença", navController = navController)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item?.let {
                        items(it) { item ->
                            PessoaisSolicitacoesPresencaCard(
                                navController = navController,
                                name = item.nome ?: "",
                                quantity = item.estado ?: "",
                                onClick = { }
                            )
                        }
                    }
                }
            }
        }

        val mostrarBotaoAdicionar =
            item?.none { it.estado == "Pendente" || it.estado == "Confirmado" } == true

        if (mostrarBotaoAdicionar) {
            FloatingActionButton(
                onClick = { showDialog = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = Color.DarkGray
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Adicionar Solicitação de Presença",
                    tint = Color.White
                )
            }
        }

    }

    if (showDialog) {
        ConfirmationDialog(diaFuncionamentoId,
            onConfirm = {
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
fun ConfirmationDialog(id: String, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Confirmação") },
        text = { Text("Tem a certeza que pretende solicitar presença neste dia de funcionamento?") },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                HorariosRepository.addSolicitacaoPresenca(id)
                Text("Sim")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Não")
            }
        }
    )
}