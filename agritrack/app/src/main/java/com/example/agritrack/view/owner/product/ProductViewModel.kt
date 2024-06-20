package com.example.agritrack.view.owner.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agritrack.pref.OwnerRepository
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: OwnerRepository) : ViewModel(){
    fun getUserProducts() = repository.getUserProducts()

    fun getProductCategory() = repository.getProductCategory()

    fun postProduct(productId: String, productName: String, productOrigin: String, productCategory: String, productComposition: String, nutritionFacts: String) = repository.postProduct(productId, productName, productOrigin, productCategory, productComposition, nutritionFacts)

    fun editProduct(productId: String, productName: String, productOrigin: String, productCategory: String, productComposition: String, nutritionFacts: String) = repository.editProduct(productId, productName, productOrigin, productCategory, productComposition, nutritionFacts)
}