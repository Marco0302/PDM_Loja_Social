package com.example.lojasocial.data.repository

import android.util.Log
import com.example.lojasocial.data.models.Beneficiario
import com.example.lojasocial.data.models.Visita
import com.google.firebase.firestore.FirebaseFirestore

object EstatisticasRepository {
    private val db by lazy { FirebaseFirestore.getInstance() }

    /**
     * Carrega todos os beneficiários, para cada um carrega a coleção 'visita'
     * e agrega estatísticas das nacionalidades -> total de visitas.
     */
    fun getEstatisticasNacionalidade(
        onSuccess: (Map<String, Int>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("beneficiario")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val beneficiarios = querySnapshot.documents.mapNotNull { doc ->
                    val benef = doc.toObject(Beneficiario::class.java)
                    // Se converter certo, guardamos o doc.id no 'id' do Beneficiario
                    benef?.copy(id = doc.id)
                }

                // Se não existe nenhum beneficiário, devolve mapa vazio
                if (beneficiarios.isEmpty()) {
                    onSuccess(emptyMap())
                    return@addOnSuccessListener
                }

                // Mapa que vai acumular => "Portuguesa" -> totalVisitas
                val nacionalidadeVisitasMap = mutableMapOf<String, Int>()

                // Contador para saber quando terminamos de processar todos
                var processedCount = 0

                // Itera todos os beneficiários
                for (benef in beneficiarios) {
                    val benefId = benef.id

                    Log.wtf("Test",benef.id);
                    db.collection("beneficiario")
                        .document(benefId)
                        .collection("visita")
                        .get()
                        .addOnSuccessListener { visitasSnap ->
                            // Numero de visitas deste beneficiario = quantidade de documentos
                            val totalVisitas = visitasSnap.size()

                            // Pegamos a nacionalidade do beneficiário
                            val nac = benef.nacionalidade
                            // Soma no mapa
                            val atual = nacionalidadeVisitasMap[nac] ?: 0
                            nacionalidadeVisitasMap[nac] = atual + totalVisitas

                            processedCount++

                            // Somente quando **TODOS** processados, chamamos onSuccess
                            if (processedCount == beneficiarios.size) {
                                onSuccess(nacionalidadeVisitasMap)
                            }
                        }
                        .addOnFailureListener { e ->
                            onFailure(e)
                        }
                }
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    /**
     * Exemplo de outra estatística, como "Visitas por Ano".
     * Vamos supor que iremos agrupar as datas (YYYY) para gerar um gráfico.
     */
    fun getVisitasPorAno(
        onSuccess: (Map<String, Int>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("beneficiario")
            .get()
            .addOnSuccessListener { snapshot ->
                val beneficiarios = snapshot.documents.mapNotNull { doc ->
                    val benef = doc.toObject(Beneficiario::class.java)
                    benef?.copy(id = doc.id)
                }

                if (beneficiarios.isEmpty()) {
                    onSuccess(emptyMap())
                    return@addOnSuccessListener
                }

                val visitasPorAno = mutableMapOf<String, Int>() // ex: "2025" -> 7, "2024" -> 10

                var processed = 0
                for (benef in beneficiarios) {
                    db.collection("beneficiario")
                        .document(benef.id)
                        .collection("visita")
                        .get()
                        .addOnSuccessListener { visitasSnap ->
                            val visitas = visitasSnap.documents.mapNotNull { visitaDoc ->
                                visitaDoc.toObject(Visita::class.java)
                            }

                            // Para cada visita, pegamos o ano da data (YYYY)
                            visitas.forEach { visita ->
                                // Ex: "2025-01-05" -> split("-") -> ["2025","01","05"]
                                // (Para ser robusto, use date libraries, mas aqui é um exemplo simples.)
                                val year = visita.data.split("-").firstOrNull() ?: "????"
                                val actualCount = visitasPorAno[year] ?: 0
                                visitasPorAno[year] = actualCount + 1
                            }

                            processed++
                            if (processed == beneficiarios.size) {
                                onSuccess(visitasPorAno)
                            }
                        }
                        .addOnFailureListener { e ->
                            onFailure(e)
                        }
                }
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

}
