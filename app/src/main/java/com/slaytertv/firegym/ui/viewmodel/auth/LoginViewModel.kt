package com.slaytertv.firegym.ui.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.slaytertv.firegym.data.model.UserItem
import com.slaytertv.firegym.data.repository.AuthRepository
import com.slaytertv.firegym.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val repository: AuthRepository
): ViewModel() {
    //modelo de vista de auth
    //variable para los datos de login a procesar
    private val _login = MutableLiveData<UiState<String>>()
    val login: LiveData<UiState<String>>
        get() = _login

    //fun login
    fun login(
        email: String,
        password: String
    ) {
        _login.value = UiState.Loading
        repository.loginUser(
            email,
            password
        ){
            _login.value = it
        }
    }

    //desloguear
    fun logout(result: () -> Unit){
        repository.logout(result)
    }
    //mandar la session para el get[preferences
    fun getSession(result: (UserItem?) -> Unit){
        repository.getSession(result)
    }
}