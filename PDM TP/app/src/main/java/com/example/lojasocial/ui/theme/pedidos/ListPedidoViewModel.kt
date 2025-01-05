package com.example.lojasocial.ui.theme.pedidos

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.lojasocial.data.models.Pedido
import com.example.lojasocial.data.repository.PedidoRepository

data class ListPedidoState(
    val list: List<Pedido> = emptyList(),
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class ListPedidoViewModel : ViewModel() {
    var state = mutableStateOf(ListPedidoState())

    fun loadListPedido() {
        PedidoRepository.getAll(
            onSuccess = { list -> state.value = state.value.copy(list = list) },
            onFailure = { exception -> state.value = state.value.copy(errorMessage = exception.message) }
        )
    }

}