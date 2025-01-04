package com.example.lojasocial.ui.theme.diasfuncionamento

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.lojasocial.data.repository.TransacaoRepository

data class AddTransacaoState(
    var descricao: String = "",
    var valor: String = "",
    var tipo: String = "Entrada",
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class AddTransactionViewModel : ViewModel() {
    var state = mutableStateOf(AddTransacaoState())

    private val descricao
        get() = state.value.descricao
    private val valor
        get() = state.value.valor
    private val tipo
        get() = state.value.tipo

    fun onDescriptionChange(newValue: String) {
        state.value = state.value.copy(descricao = newValue)
        state.value = state.value.copy(errorMessage = null)
    }

    fun onAmountChange(newValue: String) {
        state.value = state.value.copy(valor = newValue)
        state.value = state.value.copy(errorMessage = null)
    }

    fun onTypeChange(newValue: String) {
        state.value = state.value.copy(tipo = newValue)
        state.value = state.value.copy(errorMessage = null)
    }

    private fun onErrorMessage(error: String){
        state.value = state.value.copy(errorMessage = error)
    }

    fun add(onSuccess: () -> Unit){
        if(descricao.isEmpty() || valor.isEmpty())
        {
            onErrorMessage("Deve preencher os campos corretamente...")
        }
        else
        {
            TransacaoRepository.addTransaction(descricao, valor.toDouble(), tipo,
                onSuccess = onSuccess, onFailure = { })
        }
    }
}