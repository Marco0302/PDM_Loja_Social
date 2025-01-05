package com.example.lojasocial.ui.theme.beneficiario

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.lojasocial.data.repository.AgregadoFamiliarRepository
import com.example.lojasocial.data.repository.BeneficiarioRepository

data class CreateAgregadoFamiliarState(
    var nome: String = "",
    var parentesco: String = "",
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class AddAgregadoFamiliarViewModel : ViewModel() {
    var state = mutableStateOf(CreateAgregadoFamiliarState())

    private val nome
        get() = state.value.nome
    private val parentesco
        get() = state.value.parentesco

    fun onNomeChange(newValue: String) {
        state.value = state.value.copy(nome = newValue)
    }

    fun onParentescoChange(newValue: String) {
        state.value = state.value.copy(parentesco = newValue)
    }

    private fun onErrorMessage(error: String){
        state.value = state.value.copy(errorMessage = error)
    }

    fun add(beneficiarioID : String, onSuccess: () -> Unit){

        if (nome.isEmpty() && parentesco.isEmpty())
        {
            onErrorMessage("Deve preencher os campos corretamente...")
        }
        else
        {
            AgregadoFamiliarRepository.addAgregadoFamiliar(beneficiarioID, nome, parentesco,
                onSuccess = onSuccess, onFailure = {} )
        }
    }

}