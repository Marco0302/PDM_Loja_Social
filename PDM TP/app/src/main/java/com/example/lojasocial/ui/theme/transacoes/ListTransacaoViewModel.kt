package com.example.lojasocial.ui.theme.transacoes

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.lojasocial.data.models.Transacao
import com.example.lojasocial.data.repository.TransacaoRepository

data class ListTransacaoState(
    val list: List<Transacao> = emptyList(),
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class ListTransactionViewModel : ViewModel() {
    var state = mutableStateOf(ListTransacaoState())

    fun loadListTransacao() {
        TransacaoRepository.getAll(
            onSuccess = { list -> state.value = state.value.copy(list = list) },
            onFailure = { exception -> state.value = state.value.copy(errorMessage = exception.message) }
        )
    }

}