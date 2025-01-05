package com.example.lojasocial.ui.theme.pedidos

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.lojasocial.data.repository.BeneficiarioRepository
import com.example.lojasocial.data.repository.PedidoRepository

data class EditPedidoState(
    var descricao: String = "",
    var estado: String = "",
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class EditPedidoViewModel : ViewModel() {
    var state = mutableStateOf(EditPedidoState())

    private val descricao
        get() = state.value.descricao
    private val estado
        get() = state.value.estado

    fun onEstadoChange(newValue: String) {
        state.value = state.value.copy(estado = newValue)
        state.value = state.value.copy(errorMessage = null)
    }

    fun onDescricaoChange(newValue: String) {
        state.value = state.value.copy(descricao = newValue)
        state.value = state.value.copy(errorMessage = null)
    }

    private fun onErrorMessage(error: String){
        state.value = state.value.copy(errorMessage = error)
    }

    fun edit(id: String, onSuccess: () -> Unit)
    {
        PedidoRepository.updatePedido(id, "Concluido", onSuccess = onSuccess, onFailure = { })
    }

}