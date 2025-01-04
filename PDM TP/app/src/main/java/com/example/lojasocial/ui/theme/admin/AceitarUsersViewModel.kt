package com.example.lojasocial.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lojasocial.data.models.User
import com.example.lojasocial.data.repository.UsersRepository

class AceitarUsersViewModel : ViewModel() {

    var pendentesList by mutableStateOf<List<User>>(emptyList())
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        carregarUsersPendentes()
    }

    fun carregarUsersPendentes() {
        UsersRepository.getUsersPendentes(
            onSuccess = { lista ->
                pendentesList = lista
            },
            onFailure = { exception ->
                errorMessage = exception.message
            }
        )
    }

    fun aceitarUser(userId: String) {
        UsersRepository.aceitarUser(
            userId = userId,
            onSuccess = {
                // Recarrega a lista ao concluir
                carregarUsersPendentes()
            },
            onFailure = { exception ->
                errorMessage = exception.message
            }
        )
    }

    fun recusarUser(userId: String) {
        UsersRepository.recusarUser(
            userId = userId,
            onSuccess = {
                // Recarrega a lista ao concluir
                carregarUsersPendentes()
            },
            onFailure = { exception ->
                errorMessage = exception.message
            }
        )
    }
}
