package com.example.lojasocial.data.repository

import com.example.lojasocial.data.models.Transacao
import com.google.firebase.firestore.FirebaseFirestore

object TransacaoRepository {
    private val db by lazy { FirebaseFirestore.getInstance() }

    fun getAll(
        onSuccess: (List<Transacao>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("transacao")
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    onFailure(error)
                } else {
                    val lista = querySnapshot?.toObjects(Transacao::class.java).orEmpty()
                    onSuccess(lista)
                }
            }
    }

    fun addTransaction(
        descricao: String,
        valor: Double,
        tipo: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        val transacao = Transacao("", descricao, valor, tipo)

        db.collection("transacao")
            .add(transacao)
            .addOnCompleteListener { documentReference ->
                val generatedId = documentReference.result.id

                db.collection("transacao")
                    .document(generatedId)
                    .update("id", generatedId)
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener { e ->
                        onFailure(e.message ?: "Erro")
                    }
            }
            .addOnFailureListener { e ->
                onFailure(e.message ?: "Erro")
            }
    }

}
