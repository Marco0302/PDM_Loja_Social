package com.example.lojasocial.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.lojasocial.data.models.HorarioFuncionamento
import com.example.lojasocial.data.models.Transacao
import com.example.lojasocial.data.repository.HorariosRepository.db
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object TransacaoRepository {
    private val db by lazy { FirebaseFirestore.getInstance() }
    private val transactionsCollection = db.collection("transacao")

    fun addTransaction(
        descricao: String,
        valor: Double,
        tipo: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        val transacao = Transacao("", descricao, valor, tipo)

        BeneficiarioRepository.db.collection("transacao")
            .add(transacao)
            .addOnCompleteListener { documentReference ->
                val generatedId = documentReference.result.id

                BeneficiarioRepository.db.collection("transacao")
                    .document(generatedId)
                    .update("id", generatedId)
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener { e ->
                        onFailure(e.message ?: "Erro ao adicionar transacao")
                    }
            }
            .addOnFailureListener { e ->
                onFailure(e.message ?: "Erro ao adicionar transacao")
            }
    }

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
}
