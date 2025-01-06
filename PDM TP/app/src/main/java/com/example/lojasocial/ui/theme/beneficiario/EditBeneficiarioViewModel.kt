package com.example.lojasocial.ui.theme.beneficiario

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.lojasocial.data.repository.BeneficiarioRepository

data class EditListState(
    var nome: String = "",
    var telefone: String = "",
    var nacionalidade: String = "",
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class EditBeneficiarioViewModel : ViewModel() {
    var state = mutableStateOf(EditListState())

    private val nome
        get() = state.value.nome
    private val telefone
        get() = state.value.telefone
    private val nacionalidade
        get() = state.value.nacionalidade


    fun onNomeChange(newValue: String) {
        state.value = state.value.copy(nome = newValue)
        state.value = state.value.copy(errorMessage = null)
    }

    fun onTelefoneChange(newValue: String) {
        state.value = state.value.copy(telefone = newValue)
        state.value = state.value.copy(errorMessage = null)
    }

    fun onNacionalidadeChange(newValue: String) {
        state.value = state.value.copy(nacionalidade = newValue)
        state.value = state.value.copy(errorMessage = null)
    }

    private fun onErrorMessage(error: String){
        state.value = state.value.copy(errorMessage = error)
    }

    fun edit(id: String, onSuccess: () -> Unit)
    {
        if(nome.isEmpty() || telefone.isEmpty() || nacionalidade.isEmpty())
        {
            onErrorMessage("Deve preencher os campos corretamente...")
        }
        else
        {
            BeneficiarioRepository.updateBeneficiario(id, nome, telefone, nacionalidade,
                onSuccess = onSuccess, onFailure = { })
        }
    }

}