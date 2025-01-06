package com.example.lojasocial.ui.theme.beneficiario

import com.example.lojasocial.data.models.Visita
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
import com.example.lojasocial.ui.theme.bars.TopBar
import com.example.lojasocial.data.repository.VisitasRepository.getVisitas
import com.example.lojasocial.ui.theme.usercontrol.VisitaCard

@Composable
fun ListVisitasView(navController: NavController, beneficiarioId: String) {
    var itemList by remember { mutableStateOf(emptyList<Visita>()) }

    LaunchedEffect(beneficiarioId) {
        getVisitas(
            beneficiarioId = beneficiarioId,
            onSuccess = { fetchedItems ->
                itemList = fetchedItems
            },
            onFailure = {

            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column {
            TopBar(title = "Histórico Visitas", navController = navController)

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(itemList) { item ->
                    VisitaCard(
                        navController = navController,
                        name = item.id ?: "",
                        quantity = item.data.toString() ?: "",
                        onClick = { }
                    )
                }
            }
        }
    }
}
