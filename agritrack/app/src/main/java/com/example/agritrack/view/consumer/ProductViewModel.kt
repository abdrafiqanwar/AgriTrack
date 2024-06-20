package com.example.agritrack.view.consumer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agritrack.data.response.ProductsItem
import com.example.agritrack.pref.ConsumerRepository
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ConsumerRepository): ViewModel() {
    fun getAllProducts() = repository.getAllProducts()

    fun searchProducts(query: String) = repository.searchProducts(query)
}