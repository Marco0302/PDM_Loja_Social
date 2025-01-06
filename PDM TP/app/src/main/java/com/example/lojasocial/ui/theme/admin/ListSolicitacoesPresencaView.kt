package com.example.lojasocial.ui.theme.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
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

@Composable
fun ListSolicitacaoPresencaView(navController: NavController, diaFuncionamentoId: String) {
    var itemsSolicitacao by remember { mutableStateOf<List<CandidaturaHorarioFuncionamento>?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Função para recarregar a lista
    fun carregarSolicitacoes() {
        isLoading = true
        CandidaturaHorarioFuncionamentoRepository.getAll(
            listId = diaFuncionamentoId,
            onSuccess = { fetchedItems ->
                itemsSolicitacao = fetchedItems
                isLoading = false
            },
            onFailure = { error ->
                errorMessage = error
                isLoading = false
            }
        )
    }

    LaunchedEffect(diaFuncionamentoId) {
        carregarSolicitacoes()
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
                errorMessage?.let {
                    // Exibe erro, caso haja
                    Text(
                        text = "Erro: $it",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                // Se não está carregando, exibe a lista
                itemsSolicitacao?.let { lista ->
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(lista) { solicitacao ->
                            SolicitacaoCard(
                                navController = navController,
                                diaFuncionamentoId = diaFuncionamentoId,
                                solicitacao = solicitacao,
                                onRefresh = {
                                    // Após atualizar estado, recarrega a lista
                                    carregarSolicitacoes()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SolicitacaoCard(
    navController: NavController,
    diaFuncionamentoId: String,
    solicitacao: CandidaturaHorarioFuncionamento,
    onRefresh: () -> Unit
) {
    // Aqui exibimos as informações básicas
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "Nome: ${solicitacao.nome}")
            Text(text = "Estado: ${solicitacao.estado}")
            Text(text = "Data: ${solicitacao.data}")

            // Se for "Pendente", mostra botões
            if (solicitacao.estado == "Pendente") {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            // Atualiza estado para "Confirmado"
                            CandidaturaHorarioFuncionamentoRepository.updateSolicitacaoEstado(
                                horarioId = diaFuncionamentoId,
                                solicitacaoId = solicitacao.id,
                                novoEstado = "Confirmado",
                                onSuccess = {
                                    onRefresh()
                                },
                                onFailure = {
                                    // Você pode mostrar um Toast ou algo similar
                                }
                            )
                        }
                    ) {
                        Text("Confirmar")
                    }

                    Button(
                        onClick = {
                            // Atualiza estado para "Rejeitado"
                            CandidaturaHorarioFuncionamentoRepository.updateSolicitacaoEstado(
                                horarioId = diaFuncionamentoId,
                                solicitacaoId = solicitacao.id,
                                novoEstado = "Rejeitado",
                                onSuccess = {
                                    onRefresh()
                                },
                                onFailure = {
                                    // Você pode mostrar um Toast ou algo similar
                                }
                            )
                        }
                    ) {
                        Text("Rejeitar")
                    }
                }
            } else {
                // Se quiser, pode exibir algo informando que já foi tratado
                Text("Esta solicitação já está em: ${solicitacao.estado}")
            }
        }
    }
}
