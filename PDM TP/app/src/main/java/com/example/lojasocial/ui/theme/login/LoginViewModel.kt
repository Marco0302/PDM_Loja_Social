package com.example.lojasocial.ui.theme.login

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.example.lojasocial.data.repository.AuthRepository

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

class LoginViewModel : ViewModel() {
    var state = mutableStateOf(LoginState())
        private set

    private val email
        get() = state.value.email
    private val password
        get() = state.value.password

    fun onEmailChange(newValue: String) {
        state.value = state.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        state.value = state.value.copy(password = newValue)
    }

    private fun onErrorMessage(error: String){
        state.value = state.value.copy(errorMessage = error)
    }

    fun login(onLoginSuccess: (String) -> Unit) {
        state.value = state.value.copy(isLoading = true, errorMessage = null)

        if (email.isEmpty() || password.isEmpty()) {
            onErrorMessage("Deve preencher os campos corretamente...")
            state.value = state.value.copy(isLoading = false)
            return
        }

        AuthRepository.login(
            email = email,
            password = password,
            onSuccess = { role ->
                state.value = state.value.copy(isLoading = false)
                onLoginSuccess(role)
            },
            onFailure = { error ->
                state.value = state.value.copy(isLoading = false)
                onErrorMessage(error)
            }
        )
    }

    fun logout(onLogoutSuccess: () -> Unit) {
        FirebaseAuth.getInstance().signOut()
        onLogoutSuccess()
    }
}