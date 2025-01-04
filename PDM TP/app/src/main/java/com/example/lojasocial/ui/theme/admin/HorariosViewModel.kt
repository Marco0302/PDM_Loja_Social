package com.example.lojasocial.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lojasocial.data.models.HorarioFuncionamento
import com.example.lojasocial.data.repository.HorariosRepository
import java.time.LocalDate
class HorariosViewModel : ViewModel() {

    var horariosList by mutableStateOf<List<HorarioFuncionamento>>(emptyList())
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        carregarHorarios()
    }

    fun carregarHorarios() {
        HorariosRepository.getHorarios(
            onSuccess = { lista ->
                horariosList = lista
            },
            onFailure = { exception ->
                errorMessage = exception.message
            }
        )
    }

    fun addHorario(dataString: String) {
        // Cria um novo objeto com a data
        val horario = HorarioFuncionamento(data = dataString)
        HorariosRepository.addHorario(
            horario,
            onSuccess = { carregarHorarios() },
            onFailure = { errorMessage = it.message }
        )
    }

    fun removeHorario(horarioId: String) {
        if (horarioId.isBlank()) return
        HorariosRepository.deleteHorario(
            horarioId,
            onSuccess = { carregarHorarios() },
            onFailure = { errorMessage = it.message }
        )
    }
}
