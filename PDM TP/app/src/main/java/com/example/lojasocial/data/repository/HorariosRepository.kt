package com.example.lojasocial.data.repository

import android.util.Log
import com.example.lojasocial.data.models.AgregadoFamiliar
import com.example.lojasocial.data.models.CandidaturaHorarioFuncionamento
import com.example.lojasocial.data.models.HorarioFuncionamento
import com.google.firebase.auth.FirebaseAuth
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
            .addSnapshotListener { querySnapshot, error ->
                if (error != null)
                {
                    onFailure(error)
                } else
                {
                    val lista = querySnapshot?.toObjects(HorarioFuncionamento::class.java).orEmpty()
                    onSuccess(lista)
                }
            }
    }

    fun addSolicitacaoPresenca(diaFuncionamentoID: String) {
        // Obter utilizador atualmente logado
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            val uid = user.uid

            db.collection("user").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.contains("nome")) {
                        val nomeUtilizador = document.getString("nome") ?: "Nome Desconhecido"

                        val solicitacao = CandidaturaHorarioFuncionamento(
                            id = "",
                            nome = nomeUtilizador,
                            estado = "Pendente",
                            data = LocalDateTime.now().toString()
                        )

                        db.collection("horariosFuncionamento")
                            .document(diaFuncionamentoID)
                            .collection("solicitacoesPresenca")
                            .add(solicitacao)
                            .addOnSuccessListener {
                                Log.d("Firebase", "Solicitação adicionada com sucesso.")
                            }
                            .addOnFailureListener { exception ->
                                Log.e("Firebase", "Erro: ${exception.message}")
                            }
                    }
                    else {
                        Log.e("Firebase", "Documento do utilizador não contém o campo 'nome'.")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("Firebase", "Erro : ${exception.message}")
                }
        } else {
            Log.e("Firebase", "Utilizador não autenticado.")
        }
    }


}
