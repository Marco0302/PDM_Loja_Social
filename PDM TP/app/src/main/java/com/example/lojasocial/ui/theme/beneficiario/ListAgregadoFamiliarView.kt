package com.example.lojasocial.ui.theme.beneficiario

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.example.lojasocial.data.models.AgregadoFamiliar
import com.example.lojasocial.ui.theme.bars.TopBar
import com.example.lojasocial.ui.theme.usercontrol.BeneficiarioCard
import com.example.lojasocial.data.repository.AgregadoFamiliarRepository.getAll
import com.example.lojasocial.ui.theme.usercontrol.AgregadoFamiliarCard

@Composable
fun ListAgregadoFamiliarView(navController: NavController, beneficiarioId: String) {
    var itemList by remember { mutableStateOf(emptyList<AgregadoFamiliar>()) }

    LaunchedEffect(beneficiarioId) {
        getAll(
            listTypeId = beneficiarioId,
            onSuccess = { fetchedItems ->
                itemList = fetchedItems
            },
            onFailure = {

            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column {
            TopBar(title = "Agregado Familiar", navController = navController)

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(itemList) { item ->
                    AgregadoFamiliarCard(
                        navController = navController,
                        name = item.nome ?: "",
                        quantity = item.parentesco ?: "",
                        onClick = { }
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate("addAgregadoFamiliar/${beneficiarioId}") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color.DarkGray
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Adicionar Agregado Familiar",
                tint = Color.White
            )
        }
    }
}