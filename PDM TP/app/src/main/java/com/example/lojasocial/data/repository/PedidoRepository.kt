package com.example.lojasocial.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.lojasocial.data.models.HorarioFuncionamento
import com.example.lojasocial.data.models.Pedido
import com.example.lojasocial.data.models.Transacao
import com.example.lojasocial.data.repository.HorariosRepository.db
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object PedidoRepository {
    private val db by lazy { FirebaseFirestore.getInstance() }

    fun getAll(
        onSuccess: (List<Pedido>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("pedido")
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    onFailure(error)
                } else {
                    val lista = querySnapshot?.toObjects(Pedido::class.java).orEmpty()
                    onSuccess(lista)
                }
            }
    }

    fun addPedido(
        beneficiarioId: String,
        descricao: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        val pedido = Pedido("", descricao, "Pendente", beneficiarioId)

        db.collection("pedido")
            .add(pedido)
            .addOnCompleteListener { documentReference ->
                val generatedId = documentReference.result.id

                db.collection("pedido")
                    .document(generatedId)
                    .update("id", generatedId)
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener { e ->
                        onFailure(e.message ?: "Erro ao adicionar pedido")
                    }
            }
            .addOnFailureListener { e ->
                onFailure(e.message ?: "Erro ao adicionar pedido")
            }
    }

    fun updatePedido(
        pedidoId: String,
        estado: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("pedido")
            .document(pedidoId)
            .update("estado", estado)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e.message ?: "Erro ao atualizar pedido")
            }
    }

}
