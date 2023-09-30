package com.slaytertv.firegym.ui.viewmodel.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.slaytertv.firegym.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    fun isUserLoggedIn(): Boolean {
        return authRepository.getCurrentUserId() != null
    }

    fun signInAnonymously(callback: (Boolean, String?) -> Unit) {
        authRepository.signInAnonymously(callback)
    }

    // Rest of your ViewModel code...
}
class SplashViewModelFactory @Inject constructor(private val auth: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(auth) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}