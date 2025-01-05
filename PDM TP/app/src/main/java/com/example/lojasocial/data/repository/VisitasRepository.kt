package com.example.lojasocial.data.repository

import com.example.lojasocial.data.models.AgregadoFamiliar
import com.example.lojasocial.data.models.Visita
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.LocalDateTime

object VisitasRepository {
    val db by lazy { FirebaseFirestore.getInstance() }

    fun getVisitas(
        beneficiarioId: String,
        onSuccess: (List<Visita>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("beneficiario")
            .document(beneficiarioId)
            .collection("visita")
            .addSnapshotListener { querySnapshot, error ->
                if (error != null)
                {
                    onFailure(error.message.toString())
                } else
                {
                    val lista = querySnapshot?.toObjects(Visita::class.java).orEmpty()
                    onSuccess(lista)
                }
            }
    }

    fun addVisita(beneficiarioId: String,onSuccess: () -> Unit, onFailure: (String) -> Unit){

        val listItem = Visita(id = "", data = LocalDate.now().toString())

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