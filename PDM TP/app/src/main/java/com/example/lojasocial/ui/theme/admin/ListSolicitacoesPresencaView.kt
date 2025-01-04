package com.example.lojasocial.ui.theme.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lojasocial.data.models.AgregadoFamiliar
import com.example.lojasocial.data.models.CandidaturaHorarioFuncionamento
import com.example.lojasocial.ui.theme.bars.TopBar
import com.example.lojasocial.ui.theme.usercontrol.BeneficiarioCard
import com.example.lojasocial.data.repository.AgregadoFamiliarRepository.fetchItems
import com.example.lojasocial.data.repository.CandidaturaHorarioFuncionamentoRepository
import com.example.lojasocial.ui.theme.usercontrol.AgregadoFamiliarCard

@Composable
fun ListSolicitacaoPresencaView(navController: NavController, diaFuncionamentoId: String) {
    var item by remember { mutableStateOf(emptyList<CandidaturaHorarioFuncionamento>()) }

    LaunchedEffect(diaFuncionamentoId) {
        CandidaturaHorarioFuncionamentoRepository.fetchItems(
            listId = diaFuncionamentoId,
            onSuccess = { fetchedItems ->
                item = fetchedItems
            },
            onFailure = {

            }
        )
    }

    TopBar("Solicitações de Presença", navController = navController)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp))
    {
        Spacer(modifier = Modifier.height(80.dp))

        LazyColumn(contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(item) { item ->
                AgregadoFamiliarCard(
                    navController = navController,
                    name = item.nome?: "",
                    quantity = item.estado?: "",
                    onClick = { }
                )
            }
        }
    }

}