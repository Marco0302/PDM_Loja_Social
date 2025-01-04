package com.example.lojasocial.data.repository

import com.example.lojasocial.data.models.AgregadoFamiliar
import com.example.lojasocial.data.models.CandidaturaHorarioFuncionamento
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

object CandidaturaHorarioFuncionamentoRepository {
    private val db by lazy { Firebase.firestore }

    fun fetchItems(
        listId: String,
        onSuccess: (List<CandidaturaHorarioFuncionamento>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("horariosFuncionamento")
            .document(listId)
            .collection("solicitacoesPresenca")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val items = querySnapshot.documents.mapNotNull { it.toObject(CandidaturaHorarioFuncionamento::class.java) }
                onSuccess(items)
            }
            .addOnFailureListener { e ->
                onFailure("Erro: ${e.message}")
            }
    }
}