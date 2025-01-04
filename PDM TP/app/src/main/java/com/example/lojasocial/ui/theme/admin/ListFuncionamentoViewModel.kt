package com.example.lojasocial.ui.theme.admin

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.lojasocial.data.models.HorarioFuncionamento
import com.example.lojasocial.data.models.Transacao
import com.example.lojasocial.data.repository.HorariosRepository
import com.example.lojasocial.data.repository.TransacaoRepository

data class ListFuncionamentoState(
    val list: List<HorarioFuncionamento> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class ListFuncionamentoViewModel : ViewModel() {
    var state = mutableStateOf(ListFuncionamentoState())

    fun loadListFuncionamento() {
        HorariosRepository.getHorarios(
            onSuccess = { list -> state.value = state.value.copy(list = list) },
            onFailure = { exception -> state.value = state.value.copy(errorMessage = exception.message) }
        )
    }

}