package com.example.lojasocial.data.repository

import com.example.lojasocial.data.models.AgregadoFamiliar
import com.example.lojasocial.data.models.Visita
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime

object VisitasRepository {
    val db by lazy { FirebaseFirestore.getInstance() }

    fun addVisita(beneficiarioId: String,onSuccess: () -> Unit, onFailure: (String) -> Unit){

        val listItem = Visita(id = "", data = LocalDateTime.now().toString())

        db.collection("beneficiario")
            .document(beneficiarioId)
            .collection("visita")
            .add(listItem)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { error ->
                onFailure("Erro: ${error.message}")
            }
    }

}