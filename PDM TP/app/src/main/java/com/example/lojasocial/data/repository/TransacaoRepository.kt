package com.example.lojasocial.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.lojasocial.data.models.HorarioFuncionamento
import com.example.lojasocial.data.models.Transacao
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object TransacaoRepository {
    private val db by lazy { FirebaseFirestore.getInstance() }
    private val transactionsCollection = db.collection("transacao")

    fun addTransaction(descricao:String, valor:Double, tipo:String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {

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
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error)
                    return@addSnapshotListener
                }

                val listTransaction = mutableListOf<Transacao>()
                value?.let {
                    for (document in it.documents) {
                        try {
                            document.data?.let { data ->
                                val transaction = Transacao.fromMap(data)
                                listTransaction.add(transaction)
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "Erro: ${document.id}", e)
                        }
                    }
                }
                onSuccess(listTransaction)
            }
        }
    }
