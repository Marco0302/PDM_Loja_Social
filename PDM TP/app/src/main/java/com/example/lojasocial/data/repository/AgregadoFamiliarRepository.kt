package com.example.lojasocial.data.repository

import com.example.lojasocial.data.models.AgregadoFamiliar
import com.example.lojasocial.data.models.HorarioFuncionamento
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
        listTypeId: String, name: String, parentesco: String, onSuccess: () -> Unit, onFailure: (String) -> Unit){
        val listItem = AgregadoFamiliar(id = "", nome = name, parentesco = parentesco)

        BeneficiarioRepository.db.collection("beneficiario")
            .document(listTypeId)
            .collection("agregadoFamiliar")
            .add(listItem)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { error ->
                onFailure("Erro: ${error.message}")
            }
    }

}