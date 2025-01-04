package com.example.lojasocial.data.models

data class Beneficiario(
    val id: String,
    var nome: String,
    var telefone: String,
    val nacionalidade: String,
    var numeroVisita: Long,
    val criadoPor: String
)
{
    companion object {
        fun fromMap(map: Map<String, Any?>): Beneficiario {
            return Beneficiario(
                map["id"] as String,
                map["nome"] as String,
                map["telefone"] as String,
                map["nacionalidade"] as String,
                map["numeroVisita"] as Long,
                map["criadoPor"] as String,
            )
        }
    }
}
