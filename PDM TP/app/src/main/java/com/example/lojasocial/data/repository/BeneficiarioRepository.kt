package com.example.lojasocial.data.repository

import com.example.lojasocial.data.models.Beneficiario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object BeneficiarioRepository {
    val db by lazy { FirebaseFirestore.getInstance() }
    private val auth = FirebaseAuth.getInstance()

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

    fun addBeneficiario(
        nome: String,
        telefone: String,
        nacionalidade: String,
        referencia: String,
        numeroElementos: String,
        notas: String,
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
                                        onFailure(e.message ?: "Erro")
                                    }
                            }
                            .addOnFailureListener { e ->
                                onFailure(e.message ?: "Erro")
                            }
                    }
                }
        }
    }

    fun updateBeneficiario(
        id: String,
        nome: String,
        telefone: String,
        nacionalidade: String,
        referencia: String,
        numeroElementos: String,
        notas: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )
    {
        val updates = mapOf(
            "nome" to nome,
            "telefone" to telefone,
            "nacionalidade" to nacionalidade,
            "referencia" to referencia,
            "numeroElementosFamiliar" to numeroElementos,
            "notas" to notas
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

}