package com.example.agritrack.view.owner.forecasting

import androidx.lifecycle.ViewModel
import com.example.agritrack.pref.OwnerRepository

class ForecastingViewModel(private val repository: OwnerRepository) : ViewModel() {
    fun getCommodityTypes() = repository.getCommodityTypes()
}