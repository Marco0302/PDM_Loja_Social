package com.example.lojasocial.ui.theme.beneficiario

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lojasocial.ui.theme.bars.TopBar
import com.example.lojasocial.ui.theme.usercontrol.BeneficiarioCard

@Composable
fun ListBeneficiarioView(navController: NavController, modifier: Modifier = Modifier) {
    val viewModel: ShowListItemsViewModel = viewModel()
    val state by viewModel.state
    var searchQuery by remember { mutableStateOf("") }

    val listaFiltrada = state.list.filter {
        it.nome?.contains(searchQuery, ignoreCase = true) == true
    }

    Box(modifier = modifier.fillMaxSize()) {

        Column {
            TopBar(title = "Lista de Beneficiários", navController = navController)

            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("pesquisar benificiario") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "") },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
            )

            LazyColumn(contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(
                    items = listaFiltrada
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

        FloatingActionButton(
            onClick = { navController.navigate("addBeneficiario") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
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

    LaunchedEffect(key1 = Unit) {
        viewModel.loadListBeneficiario()
    }
}