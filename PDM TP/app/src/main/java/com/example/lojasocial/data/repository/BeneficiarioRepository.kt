package com.example.lojasocial.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.lojasocial.data.models.Beneficiario
import com.example.lojasocial.data.models.HorarioFuncionamento
import com.example.lojasocial.data.models.Transacao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

object BeneficiarioRepository {
    val db by lazy { FirebaseFirestore.getInstance() }
    private val auth = FirebaseAuth.getInstance()

    fun createBeneficiario(
        nome: String,
        telefone: String,
        nacionalidade: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        val uid = auth.currentUser?.uid
        if (uid != null) {
            db.collection("user").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.contains("nome")) {
                        val userName = document.getString("nome") ?: ""

                        val beneficiario = Beneficiario(
                            id = "", nome = nome, telefone = telefone,
                            nacionalidade = nacionalidade, criadoPor = userName
                        )

                        db.collection("beneficiario")
                            .add(beneficiario)
                            .addOnCompleteListener { documentReference ->
                                val generatedId = documentReference.result.id

                                db.collection("beneficiario")
                                    .document(generatedId)
                                    .update("id", generatedId)
                                    .addOnSuccessListener {
                                        onSuccess()
                                    }
                                    .addOnFailureListener { e ->
                                        onFailure(e.message ?: "Erro ao adicionar beneficiário")
                                    }
                            }
                            .addOnFailureListener { e ->
                                onFailure(e.message ?: "Erro ao adicionar beneficiário")
                            }
                    }
                }
        }
    }

    fun getAll(
        onSuccess: (List<Beneficiario>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("beneficiario")
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    onFailure("Erro: ${error.message}")
                } else {
                    val lista = querySnapshot?.toObjects(Beneficiario::class.java).orEmpty()
                    onSuccess(lista)
                }
            }
    }

    fun updateBeneficiario(
        id: String,
        nome: String,
        telefone: String,
        nacionalidade: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )
    {
        val updates = mapOf(
            "nome" to nome,
            "telefone" to telefone,
            "nacionalidade" to nacionalidade,
        )

        db.collection("beneficiario")
            .document(id)
            .update(updates)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure("Erro: ${e.message}")
            }
    }

    fun deleteBeneficiario(id: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("beneficiario").document(id)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun addNovaVisita(id: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit){
        val db = FirebaseFirestore.getInstance()

        db.collection("beneficiario").document(id)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    db.collection("beneficiario").document(id)
                        .update("numeroVisita", FieldValue.increment(1))
                        .addOnSuccessListener {
                            onSuccess()
                        }
                        .addOnFailureListener { exception ->
                            onFailure(exception)
                        }
                } else {
                    onFailure(Exception("Documento não encontrado"))
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

}