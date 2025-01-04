package com.example.lojasocial.data.models

data class Transacao (
    val id: String = "",
    val descricao: String = "",
    val valor: Double = 0.0,
    val tipo: String = "",
    val data: Long = System.currentTimeMillis()
) {
    companion object {
        fun fromMap(map: Map<String, Any>): Transacao {
            return Transacao(
                map["id"] as String,
                map["descricao"] as String,
                map["valor"] as Double,
                map["tipo"] as String,
                map["data"] as Long,
            )
        }
    }
}