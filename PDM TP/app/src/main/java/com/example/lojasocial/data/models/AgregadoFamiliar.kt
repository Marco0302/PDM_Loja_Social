package com.example.lojasocial.data.models

data class AgregadoFamiliar(
    var id: String = "",
    var nome: String = "",
    var parentesco: String = ""
)
{
    companion object {
        fun fromMap(map: Map<String, Any?>): AgregadoFamiliar {
            return AgregadoFamiliar(
                map["id"] as String,
                map["nome"] as String,
                map["parentesco"] as String,
            )
        }
    }
}
