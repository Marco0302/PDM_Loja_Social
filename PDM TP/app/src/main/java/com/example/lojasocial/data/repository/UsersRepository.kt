package com.example.lojasocial.data.repository

import com.example.lojasocial.data.models.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

object UsersRepository {
    private val firestore by lazy { FirebaseFirestore.getInstance() }

    /**
     * Busca todos os usuários com estado = "pendente"
     */
    fun getUsersPendentes(
        onSuccess: (List<User>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firestore.collection("user")
            .whereEqualTo("estado", "pendente")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val listaPendentes = querySnapshot.toObjects(User::class.java)
                onSuccess(listaPendentes)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    /**
     * Aceita o usuário, atualizando estado para "aceite"
     */
    fun aceitarUser(
        userId: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firestore.collection("user")
            .document(userId)
            .update("estado", "aceite")
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    /**
     * Recusa o usuário, removendo do Firestore
     */
    fun recusarUser(
        userId: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firestore.collection("user")
            .document(userId)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}
