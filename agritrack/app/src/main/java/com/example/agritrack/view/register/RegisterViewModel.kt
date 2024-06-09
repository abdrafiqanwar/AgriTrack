package com.example.agritrack.view.register

import androidx.lifecycle.ViewModel
import com.example.agritrack.pref.AuthRepository

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {
    fun register(name: String, email: String, password: String, role: String) = repository.register(name, email, password, role)
}