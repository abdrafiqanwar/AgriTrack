package com.example.agritrack.view.login

import androidx.lifecycle.ViewModel
import com.example.agritrack.pref.AuthRepository

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password)
}