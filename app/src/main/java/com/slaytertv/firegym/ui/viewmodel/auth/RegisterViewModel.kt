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
class RegisterViewModel@Inject constructor(
    val repository: AuthRepository
): ViewModel() {
    //modelo de vista de auth
    //variable para guardar los datos del registro
    private val _register = MutableLiveData<UiState<String>>()
    //para ver los datos actuales
    val register: LiveData<UiState<String>>
        get() = _register

    //funcion cuando se registren
    fun register(
        //pedimos los datos
        email: String,
        password: String,
        user: UserItem
    ) {
        //
        _register.value = UiState.Loading
        repository.registerUser(
            //mandamos los datos
            email = email,
            password = password,
            user = user
        ) { _register.value = it }
    }
}
