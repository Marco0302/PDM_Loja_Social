package com.example.lojasocial.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object AuthRepository {
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val firestore by lazy { FirebaseFirestore.getInstance() }

    fun login(
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        firestore.collection("user").document(userId).get()
                            .addOnSuccessListener { document ->
                                if (document.exists()) {
                                    val role = document.getString("role") ?: "default"
                                    onSuccess(role) // Passa o role para o sucesso
                                } else {
                                    onFailure("Dados do utilizador não encontrados.")
                                }
                            }
                            .addOnFailureListener { e ->
                                onFailure("Erro ao buscar dados: ${e.message}")
                            }
                    } else {
                        onFailure("Erro ao obter ID do usuário.")
                    }
                } else {
                    onFailure(task.exception?.localizedMessage ?: "Erro ao fazer login.")
                }
            }
    }

    fun getUserRole(
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            firestore.collection("user").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val role = document.getString("role") ?: "default"
                        onSuccess(role)
                    } else {
                        onFailure("Dados do utilizador não encontrados.")
                    }
                }
                .addOnFailureListener { e ->
                    onFailure("Erro ao buscar dados: ${e.message}")
                }
        } else {
            onFailure("Usuário não autenticado.")
        }
    }



    fun logout(onSuccess: () -> Unit) {
        auth.signOut()
        onSuccess()
    }
}
