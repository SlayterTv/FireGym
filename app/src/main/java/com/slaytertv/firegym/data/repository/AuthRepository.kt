package com.slaytertv.firegym.data.repository

import com.slaytertv.firegym.data.model.UserItem
import com.slaytertv.firegym.util.UiState

interface AuthRepository {
    //estas seran para registrar
    fun registerUser(email: String, password: String, user: UserItem, result: (UiState<String>) -> Unit)
    //actualizar usuario
    fun updateUserInfo(user: UserItem, result: (UiState<String>) -> Unit)
    //loguear usuario
    fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit)
    //si se olvido el password
    fun forgotPassword(email: String, result: (UiState<String>) -> Unit)
    //si dse desloguea
    fun logout(result: () -> Unit)
    //session
    fun storeSession(id: String, result: (UserItem?) -> Unit)
    //mandar session
    fun getSession(result: (UserItem?) -> Unit)
    //auth anonymous
    fun signInAnonymously(callback: (Boolean, String?) -> Unit)
    fun getCurrentUserId(): String?
}