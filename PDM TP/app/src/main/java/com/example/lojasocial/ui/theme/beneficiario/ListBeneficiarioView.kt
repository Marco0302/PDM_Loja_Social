package com.example.lojasocial.ui.theme.beneficiario

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.example.lojasocial.ui.theme.usercontrol.BeneficiarioCard

@Composable
fun ListBeneficiarioView(navController: NavController, modifier: Modifier = Modifier) {
    val viewModel: ShowListItemsViewModel = viewModel()
    val state by viewModel.state

    Box(modifier = modifier.fillMaxSize()) {
        // Coloca a lista de beneficiários dentro da Box
        Column {
            TopBar(title = "Lista de Beneficiários", navController = navController)

            LazyColumn(contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(
                    items = state.listItems
                ) { _, item ->
                    BeneficiarioCard(
                        navController = navController,
                        id = item.id ?: "",
                        name = item.nome ?: "",
                        description = item.telefone ?: "",
                        onClick = {
                            navController.navigate("listAgregadoFamiliar/${item.id}")
                        }
                    )
                }
            }
        }

        // Botão flutuante dentro do Box (fora do Column), agora o align vai funcionar corretamente
        FloatingActionButton(
            onClick = { navController.navigate("addBeneficiario") },
            modifier = Modifier
                .align(Alignment.BottomEnd) // Alinha o botão no canto inferior direito
                .padding(16.dp),
            containerColor = Color.DarkGray
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Adicionar Beneficiário",
                tint = Color.White
            )
        }
    }

    // Carregar lista de itens ao iniciar
    LaunchedEffect(key1 = Unit) {
        viewModel.loadListItems()
    }
}