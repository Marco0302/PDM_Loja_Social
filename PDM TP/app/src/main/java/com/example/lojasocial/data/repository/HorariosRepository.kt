package com.example.lojasocial.data.repository

import com.example.lojasocial.data.models.AgregadoFamiliar
import com.example.lojasocial.data.models.CandidaturaHorarioFuncionamento
import com.example.lojasocial.data.models.HorarioFuncionamento
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime

object HorariosRepository {
    val db by lazy { FirebaseFirestore.getInstance() }

    fun addHorario(
        horario: HorarioFuncionamento,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val docRef = db.collection("horariosFuncionamento").document()
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
        db.collection("horariosFuncionamento").document(horarioId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun getHorarios(
        onSuccess: (List<HorarioFuncionamento>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("horariosFuncionamento")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val lista = querySnapshot.toObjects(HorarioFuncionamento::class.java)
                onSuccess(lista)
            }
            .addOnFailureListener { onFailure(it) }
    }

    fun addSolicitacaoPresenca(
        diaFuncionamentoID: String
    ) {

        val solicitacao = CandidaturaHorarioFuncionamento(id = "", nome = "Marco Oliveira", estado = "Pendente", data = LocalDateTime.now().toString() )

        db.collection("horariosFuncionamento")
            .document(diaFuncionamentoID)
            .collection("solicitacoesPresenca")
            .add(solicitacao)
            .addOnSuccessListener {
                //onSuccess()
            }
            .addOnFailureListener { //onFailure(it)
            }
        }
    }
