package com.example.lojasocial.ui.theme.diasfuncionamento

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.lojasocial.data.models.HorarioFuncionamento
import com.example.lojasocial.data.models.Transacao
import com.example.lojasocial.data.repository.HorariosRepository
import com.example.lojasocial.data.repository.TransacaoRepository

data class ListHorarioFuncionamentoState(
    val list: List<HorarioFuncionamento> = emptyList(),
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class ListHorarioFuncionamentoViewModel : ViewModel() {
    var state = mutableStateOf(ListHorarioFuncionamentoState())

    fun loadListHorariosFuncionamento() {
        HorariosRepository.getHorarios(
            onSuccess = { list -> state.value = state.value.copy(list = list) },
            onFailure = { exception ->
                //erro ${exception.message}")
            }
        )
    }

}