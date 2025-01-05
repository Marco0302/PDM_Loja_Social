package com.example.lojasocial.data.repository

import android.util.Log
import com.example.lojasocial.data.models.AgregadoFamiliar
import com.example.lojasocial.data.models.CandidaturaHorarioFuncionamento
import com.example.lojasocial.data.models.HorarioFuncionamento
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale

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
                if (error != null) {
                    onFailure(error)
                } else {
                    if (querySnapshot != null) {
                        val documents = querySnapshot.documents

                        // Lista final onde armazenaremos os HorarioFuncionamento
                        // já com o campo vagasDisponiveis calculado
                        val finalList = mutableListOf<HorarioFuncionamento>()

                        // Se não há nenhum documento
                        if (documents.isEmpty()) {
                            onSuccess(emptyList())
                            return@addSnapshotListener
                        }

                        var docsProcessed = 0

                        // Para cada horário
                        for (doc in documents) {
                            val horario = doc.toObject(HorarioFuncionamento::class.java)
                            if (horario != null) {
                                val horarioId = doc.id

                                // Buscar presenças "Confirmado"
                                db.collection("horariosFuncionamento")
                                    .document(horarioId)
                                    .collection("solicitacoesPresenca")
                                    .whereEqualTo("estado", "Confirmado")
                                    .get()
                                    .addOnSuccessListener { subSnap ->
                                        // Nº de presenças confirmadas
                                        val confirmadosCount = subSnap.size()

                                        // Calcula vagasDisponiveis = numeroMaxVoluntarios - confirmadosCount
                                        val vagasDisponiveisCalculadas = horario.numeroMaxVoluntarios - confirmadosCount

                                        val horarioAtualizado = horario.copy(
                                            id = horarioId,
                                            // Aqui sobrescrevemos o campo vagasDisponiveis
                                            // com a subtração
                                            vagasDisponiveis = vagasDisponiveisCalculadas
                                        )
                                        finalList.add(horarioAtualizado)

                                        docsProcessed++
                                        if (docsProcessed == documents.size) {
                                            onSuccess(finalList)
                                        }
                                    }
                                    .addOnFailureListener { subError ->
                                        onFailure(subError)
                                    }
                            } else {
                                // Se não conseguiu converter este doc num HorarioFuncionamento
                                docsProcessed++
                                if (docsProcessed == documents.size) {
                                    onSuccess(finalList)
                                }
                            }
                        }
                    } else {
                        onSuccess(emptyList())
                    }
                }
            }
    }


    fun addSolicitacaoPresenca(diaFuncionamentoID: String) {
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            val uid = user.uid

            db.collection("user").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.contains("nome")) {
                        val nomeUtilizador = document.getString("nome") ?: "Nome Desconhecido"

                        val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                        val currentDate = dateFormatter.format(Date())

                        val solicitacao = CandidaturaHorarioFuncionamento(
                            id = "",
                            nome = nomeUtilizador,
                            estado = "Pendente",
                            data = currentDate.toString()
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
