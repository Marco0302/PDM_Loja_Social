package com.example.lojasocial.ui.theme.beneficiario

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lojasocial.ui.theme.bars.TopBar
import com.example.lojasocial.ui.theme.usercontrol.BeneficiarioCard

@Composable
fun ListBeneficiarioView(navController: NavController, modifier: Modifier = Modifier) {
    val viewModel: ShowListItemsViewModel = viewModel()
    val state by viewModel.state

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            TopBar(title = "Lista de Beneficiários", navController = navController)

            Button(onClick = {
                navController.navigate("addBeneficiario")
            },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            {
                Text(text = "Adicionar Beneficiário")
            }

            LazyColumn(contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(
                    items = state.listItems
                ) { _, item ->
                    BeneficiarioCard (
                        navController = navController,
                        id = item.id?: "",
                        name = item.nome?: "",
                        description = item.telefone?: "",
                        onClick = {
                            navController.navigate("listAgregadoFamiliar/${item.id}")
                        }
                    )
                }
            }
        }
    }

    LaunchedEffect (key1 = Unit){
        viewModel.loadListItems()
    }
}