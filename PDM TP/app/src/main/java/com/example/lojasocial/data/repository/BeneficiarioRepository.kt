package com.example.lojasocial.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.lojasocial.data.models.Beneficiario
import com.example.lojasocial.data.models.HorarioFuncionamento
import com.google.firebase.auth.FirebaseAuth
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
                            nacionalidade = nacionalidade, numeroVisita = 0, criadoPor = userName
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
        onFailure: (Exception) -> Unit
    ) {
        db.collection("beneficiario")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error)
                    return@addSnapshotListener
                }

                val listBeneficiary = mutableListOf<Beneficiario>()
                value?.let {
                    for (document in it.documents) {
                        try {
                            document.data?.let { data ->
                                val beneficiary = Beneficiario.fromMap(data)
                                listBeneficiary.add(beneficiary)
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "Erro: ${document.id}", e)
                        }
                    }
                }
                onSuccess(listBeneficiary)
            }
    }

    fun updateBeneficiario(
        id: String,
        nome: String,
        telefone: String,
        nacionalidade: String,
        agregadoFamiliar: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )
    {
        val updates = mapOf(
            "nome" to nome,
            "telefone" to telefone,
            "nacionalidade" to nacionalidade,
            "agregadoFamiliar" to agregadoFamiliar,
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