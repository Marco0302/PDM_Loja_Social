package com.example.lojasocial.ui.theme.pedidos

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
fun EditPedidoView(navController: NavController, id: String) {
    val viewModel: EditPedidoViewModel = viewModel()
    val state by viewModel.state
    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(id) {
        db.collection("pedido").document(id).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    state.descricao = document.getString("descricao") ?: ""
                    state.estado = document.getString("estado") ?: ""
                }
            }
            .addOnFailureListener { e ->
                Log.e("EditPedido", "Erro: ${e.message}")
            }
    }

    TopBar(title = "Editar Pedido Especial", navController = navController)

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
            value = state.descricao,
            onValueChange = viewModel::onDescricaoChange,
            label = { Text("descricao") },
            leadingIcon = { Icon(Icons.Filled.Info, contentDescription = "") },
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
            Text(if (state.isLoading) "a carregar..." else "marcar como concluído")
        }

        Spacer(modifier = Modifier.height(16.dp))
        state.errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

    }
}