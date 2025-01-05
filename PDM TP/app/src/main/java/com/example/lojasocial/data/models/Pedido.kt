package com.example.lojasocial.data.models

data class Pedido (
    val id: String = "",
    val descricao: String = "",
    var estado: String = "",
    val beneficiarioId: String = "",
    val data: Long = System.currentTimeMillis()
)