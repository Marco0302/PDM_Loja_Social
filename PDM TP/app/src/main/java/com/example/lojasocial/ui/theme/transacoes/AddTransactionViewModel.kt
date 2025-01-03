package com.example.lojasocial.ui.theme.transacoes

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.lojasocial.data.models.Transaction
import com.example.lojasocial.data.repository.TransactionRepository
import kotlinx.coroutines.launch

data class AddTransactionState(
    var amount: String = "",
    var description: String = "",
    var type: String = "",
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class AddTransactionViewModel : ViewModel() {
    var state = mutableStateOf(AddTransactionState())
        private set

    private val amount
        get() = state.value.amount
    private val description
        get() = state.value.description
    private val type
        get() = state.value.type

    fun onDescriptionChange(newValue: String) {
        state.value = state.value.copy(description = newValue)
        state.value = state.value.copy(errorMessage = null)
    }

    fun onAmountChange(newValue: String) {
        state.value = state.value.copy(amount = newValue)
        state.value = state.value.copy(errorMessage = null)
    }

    fun onTypeChange(newValue: String) {
        state.value = state.value.copy(type = newValue)
        state.value = state.value.copy(errorMessage = null)
    }

    private fun onErrorMessage(error: String){
        state.value = state.value.copy(errorMessage = error)
    }

    fun addTransaction(description: String, amount: Double, type: String, onSuccess: () -> Unit
                       , onFailure: (String) -> Unit): Boolean {
        if(state.value.description.isEmpty())
        {
            onErrorMessage("Deve preencher os campos corretamente...");
            return false;
        }
        else
        {
            viewModelScope.launch {
                try {
                    TransactionRepository.addTransaction(
                        Transaction(
                            description = description,
                            amount = amount,
                            type = type
                        )
                    )
                    onSuccess()
                } catch (e: Exception) {
                    onFailure(e.message ?: "Erro desconhecido")
                }
            }
            return true;
        }
    }
}