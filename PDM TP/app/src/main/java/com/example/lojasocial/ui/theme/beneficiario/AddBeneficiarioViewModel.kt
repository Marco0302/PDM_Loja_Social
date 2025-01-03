package com.example.lojasocial.ui.theme.beneficiario

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.lojasocial.data.repository.BeneficiarioRepository
import com.example.lojasocial.data.utils.BeneficiarioUtils

data class CreateBeneficiarioState(
    var nome: String = "",
    var telefone: String = "",
    override var nacionalidade: String = "",
    var editedNacionalidade: String = "",
    var agregadoFamiliar: String = "",
    var numeroVisitas: Int = 0,
    val isLoading: Boolean = false,
    var errorMessage: String? = null
) : BeneficiarioUtils

class AddBeneficiarioViewModel : ViewModel() {
    var state = mutableStateOf(CreateBeneficiarioState())
        private set

    private val nome
        get() = state.value.nome
    private val telefone
        get() = state.value.telefone
    private val nacionalidade
        get() = state.value.nacionalidade
    private val agregadoFamiliar
        get() = state.value.agregadoFamiliar

    fun onNomeChange(newValue: String) {
        state.value = state.value.copy(nome = newValue)
    }

    fun onTelefoneChange(newValue: String) {
        state.value = state.value.copy(telefone = newValue)
    }

    fun onNacionalidadeChange(newValue: String) {
        state.value = state.value.copy(nacionalidade = newValue)
    }

    fun onAgregadoFamiliarChange(newValue: String) {
        state.value = state.value.copy(agregadoFamiliar = newValue)
    }

    fun create(onSuccess: () -> Unit){
        BeneficiarioRepository.createBeneficiario(
            nome = nome,
            telefone = telefone,
            nacionalidade = "Portugal",
            agregadoFamiliar = agregadoFamiliar,
            onSuccess = onSuccess,
            onFailure = { })
    }
}