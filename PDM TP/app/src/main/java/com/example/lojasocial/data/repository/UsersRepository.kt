package com.example.lojasocial.data.repository

import com.example.lojasocial.data.models.User
import com.google.firebase.firestore.FirebaseFirestore

object UsersRepository {
    private val db by lazy { FirebaseFirestore.getInstance() }

    fun getUsersPendentes(
        onSuccess: (List<User>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("user")
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

    fun aceitarUser(
        userId: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("user")
            .document(userId)
            .update("estado", "aceite")
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun recusarUser(
        userId: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("user")
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
