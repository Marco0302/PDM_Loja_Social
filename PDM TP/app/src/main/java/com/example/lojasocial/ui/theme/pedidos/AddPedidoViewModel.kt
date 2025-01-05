package com.example.lojasocial.ui.theme.pedidos

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.lojasocial.data.repository.AgregadoFamiliarRepository
import com.example.lojasocial.data.repository.PedidoRepository

data class CreatePedidoState(
    var descricao: String = "",
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class AddPedidoViewModel : ViewModel() {
    var state = mutableStateOf(CreatePedidoState())

    private val descricao
        get() = state.value.descricao

    fun onDescricaoChange(newValue: String) {
        state.value = state.value.copy(descricao = newValue)
    }

    private fun onErrorMessage(error: String){
        state.value = state.value.copy(errorMessage = error)
    }

    fun add(beneficiarioID : String, onSuccess: () -> Unit){

        if (descricao.isEmpty())
        {
            onErrorMessage("Deve preencher os campos corretamente...")
        }
        else
        {
            PedidoRepository.addPedido(beneficiarioID, descricao, onSuccess = onSuccess, onFailure = {} )
        }
    }

}