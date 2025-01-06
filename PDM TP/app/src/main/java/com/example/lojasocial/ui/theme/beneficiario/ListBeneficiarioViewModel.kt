package com.example.lojasocial.ui.theme.beneficiario

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.lojasocial.data.models.Beneficiario
import com.example.lojasocial.data.repository.BeneficiarioRepository

data class ListBeneficiarioItemsState(
    val list: List<Beneficiario> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class ShowListItemsViewModel : ViewModel(){
    var state = mutableStateOf(ListBeneficiarioItemsState())

    fun loadListBeneficiario() {
        BeneficiarioRepository.getAll(
            onSuccess = { list -> state.value = state.value.copy(list = list) },
            onFailure = { exception -> state.value = state.value.copy(errorMessage = exception) }
        )
    }

}