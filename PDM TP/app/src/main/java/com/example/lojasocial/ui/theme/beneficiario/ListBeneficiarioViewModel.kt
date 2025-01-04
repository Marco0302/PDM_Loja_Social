package com.example.lojasocial.ui.theme.beneficiario

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.lojasocial.data.models.Beneficiario
import com.example.lojasocial.data.repository.BeneficiarioRepository

data class ListBeneficiarioItemsState(
    val listItems: List<Beneficiario> = emptyList(),
    //val items: List<Item> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ShowListItemsViewModel : ViewModel(){
    var state = mutableStateOf(ListBeneficiarioItemsState())
        private set

    fun loadListItems(){
        BeneficiarioRepository.getAll{ listItems ->
            state.value = state.value.copy(
                listItems = listItems
            )
        }
    }
}