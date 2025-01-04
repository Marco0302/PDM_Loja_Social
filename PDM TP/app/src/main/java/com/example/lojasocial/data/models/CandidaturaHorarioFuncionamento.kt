package com.example.lojasocial.data.models

data class CandidaturaHorarioFuncionamento (
    val id: String = "",
    val nome: String = "",
    val estado: String = "",
    val data: String = ""
) {
    companion object {
        fun fromMap(map: Map<String, Any>): CandidaturaHorarioFuncionamento {
            return CandidaturaHorarioFuncionamento(
                map["id"] as String,
                map["nome"] as String,
                map["estado"] as String,
                map["data"] as String,
            )
        }
    }
}