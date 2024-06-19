package com.example.agritrack.view.owner.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agritrack.pref.OwnerRepository
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: OwnerRepository) : ViewModel(){
    fun getUserProducts() = repository.getUserProducts()
}