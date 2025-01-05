package com.example.lojasocial.ui.theme.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.lojasocial.data.models.User
import com.example.lojasocial.ui.theme.bars.TopBar
import com.example.lojasocial.ui.viewmodels.AceitarUsersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AceitarUsersScreen(
    navController: NavController,
    viewModel: AceitarUsersViewModel = viewModel()
) {
    val pendentes = viewModel.pendentesList
    val errorMessage = viewModel.errorMessage

    Scaffold(
        topBar = {
            TopBar(title = "Aceitar Utilizadores Pendentes", navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Lista de Utilizadores Pendentes",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            )

            // Exibe mensagem de erro, se existir
            errorMessage?.let {
                Text(
                    text = "Erro: $it",
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de pendentes
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(pendentes) { user ->
                    UserItem(
                        user = user,
                        onAceitar = { userId ->
                            viewModel.aceitarUser(userId)
                        },
                        onRecusar = { userId ->
                            viewModel.recusarUser(userId)
                        }
                    )
                    Divider()
                }
            }
        }
    }
}

@Composable
fun UserItem(
    user: User,
    onAceitar: (String) -> Unit,
    onRecusar: (String) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = "Nome: ${user.nome}")
        Text(text = "Email: ${user.email}")
        Text(text = "Telefone: ${user.telefone}")
        Text(text = "Estado: ${user.role}")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { user.id?.let { onAceitar(it) } }) {
                Text("Aceitar")
            }
            Button(onClick = { user.id?.let { onRecusar(it) } }) {
                Text("Recusar")
            }
        }
    }
}
