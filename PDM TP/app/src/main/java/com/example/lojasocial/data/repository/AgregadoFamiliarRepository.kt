package com.example.lojasocial.data.repository

import com.example.lojasocial.data.models.AgregadoFamiliar
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

object AgregadoFamiliarRepository {
    private val db by lazy { Firebase.firestore }

    fun fetchItems(
        listTypeId: String,
        onSuccess: (List<AgregadoFamiliar>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("beneficiario")
            .document(listTypeId)
            .collection("agregadoFamiliar")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val items = querySnapshot.documents.mapNotNull { it.toObject(AgregadoFamiliar::class.java) }
                onSuccess(items)
            }
            .addOnFailureListener { e ->
                onFailure("Erro: ${e.message}")
            }
    }

    fun addAgregadoFamiliar(
        listTypeId: String, name: String, parentesco: String){
        val listItem = AgregadoFamiliar(id = "", nome = name, parentesco = parentesco)

        BeneficiarioRepository.db.collection("beneficiario")
            .document(listTypeId)
            .collection("agregadoFamiliar")
            .add(listItem)
            .addOnSuccessListener {
                //onSuccess()
            }
            .addOnFailureListener { exception ->
                //onFailure()
            }
    }
}