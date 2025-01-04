package com.example.lojasocial.ui.theme.transacoes

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.lojasocial.data.models.Transacao
import com.example.lojasocial.data.repository.TransacaoRepository

data class ListTransactionState(
    val listTransaction: List<Transacao> = emptyList(),
    var description: String = "",
    var amount: String = "",
    var type: String = "",
    var date: Long = System.currentTimeMillis(),
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class ListTransactionViewModel : ViewModel() {
    var state = mutableStateOf(ListTransactionState())
        private set

    fun loadListTransaction() {
        TransacaoRepository.getAll { listTransaction ->
            state.value = state.value.copy(
                listTransaction = listTransaction
            )
        }
    }
}