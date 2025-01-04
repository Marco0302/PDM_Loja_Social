package com.example.lojasocial.data.repository

import com.example.lojasocial.data.models.HorarioFuncionamento
import com.google.firebase.firestore.FirebaseFirestore

object HorariosRepository {
    val firestore by lazy { FirebaseFirestore.getInstance() }

    fun addHorario(
        horario: HorarioFuncionamento,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val docRef = firestore.collection("horariosFuncionamento").document()
        val horarioComId = horario.copy(id = docRef.id)
        docRef.set(horarioComId)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun deleteHorario(
        horarioId: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firestore.collection("horariosFuncionamento").document(horarioId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun getHorarios(
        onSuccess: (List<HorarioFuncionamento>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firestore.collection("horariosFuncionamento")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val lista = querySnapshot.toObjects(HorarioFuncionamento::class.java)
                onSuccess(lista)
            }
            .addOnFailureListener { onFailure(it) }
    }
}
