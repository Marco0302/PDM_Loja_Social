package com.example.lojasocial.ui.theme.beneficiario

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
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
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun EditBeneficiarioView(navController: NavController, id: String) {
    val viewModel: EditBeneficiarioViewModel = viewModel()
    val state by viewModel.state
    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(id) {
        db.collection("beneficiario").document(id).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    state.nome = document.getString("nome") ?: ""
                    state.telefone = document.getString("telefone") ?: ""
                    state.nacionalidade = document.getString("nacionalidade") ?: ""
                    state.referencia = document.getString("referencia") ?: ""
                    state.numeroElementosFamiliar = document.getString("numeroElementosFamiliar") ?: ""
                    state.notas = document.getString("notas") ?: ""
                }
            }
            .addOnFailureListener { e ->
                Log.e("EditBeneficiario", "Erro: ${e.message}")
            }
    }

    TopBar(title = "Editar Beneficiário", navController = navController)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Spacer(modifier = Modifier.height(80.dp))

        TextField(
            value = state.nome,
            onValueChange = viewModel::onNomeChange,
            label = { Text("nome") },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.telefone,
            onValueChange = viewModel::onTelefoneChange,
            label = { Text("telefone") },
            leadingIcon = { Icon(Icons.Filled.Phone, contentDescription = "") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.nacionalidade,
            onValueChange = viewModel::onNacionalidadeChange,
            label = { Text("nacionalidade") },
            leadingIcon = { Icon(Icons.Filled.Place, contentDescription = "") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.referencia,
            onValueChange = viewModel::onReferenciaChange,
            label = { Text("referencia") },
            leadingIcon = { Icon(Icons.Filled.Info, contentDescription = "") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.numeroElementosFamiliar,
            onValueChange = viewModel::onNumeroELementosFamiliarhange,
            label = { Text("nº elementos") },
            leadingIcon = { Icon(Icons.Filled.Face, contentDescription = "") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.notas,
            onValueChange = viewModel::onNotashange,
            label = { Text("notas") },
            leadingIcon = { Icon(Icons.Filled.Edit, contentDescription = "") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
               viewModel.edit(id, onSuccess = { navController.popBackStack()})
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
            enabled = !state.isLoading
        )
        {
            Text(if (state.isLoading) "a carregar..." else "editar")
        }

        Spacer(modifier = Modifier.height(16.dp))
        state.errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

    }
}