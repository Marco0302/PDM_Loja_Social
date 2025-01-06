package com.example.lojasocial.data.models

data class Transacao (
    val id: String = "",
    val descricao: String = "",
    val valor: Double = 0.0,
    val tipo: String = "",
    val data: Long = System.currentTimeMillis()
)