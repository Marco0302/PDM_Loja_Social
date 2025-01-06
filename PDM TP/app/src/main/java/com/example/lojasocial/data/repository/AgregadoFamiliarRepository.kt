package com.example.lojasocial.data.repository

import com.example.lojasocial.data.models.AgregadoFamiliar
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

object AgregadoFamiliarRepository {
    private val db by lazy { Firebase.firestore }

    fun getAll(
        listTypeId: String,
        onSuccess: (List<AgregadoFamiliar>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("beneficiario")
            .document(listTypeId)
            .collection("agregadoFamiliar")
            .addSnapshotListener { querySnapshot, error ->
                if (error != null)
                {
                    onFailure(error.message.toString())
                } else
                {
                    val lista = querySnapshot?.toObjects(AgregadoFamiliar::class.java).orEmpty()
                    onSuccess(lista)
                }
            }
    }

    fun addAgregadoFamiliar(
        beneficiarioId: String, name: String, parentesco: String, onSuccess: () -> Unit, onFailure: (String) -> Unit){
        val agregado = AgregadoFamiliar(id = "", nome = name, parentesco = parentesco)

        db.collection("beneficiario")
            .document(beneficiarioId)
            .collection("agregadoFamiliar")
            .add(agregado)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { error ->
                onFailure("Erro: ${error.message}")
            }
    }

}