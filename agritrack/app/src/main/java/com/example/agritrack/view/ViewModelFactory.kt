package com.example.agritrack.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.agritrack.di.Injection
import com.example.agritrack.pref.AuthRepository
import com.example.agritrack.pref.ConsumerRepository
import com.example.agritrack.pref.OwnerRepository
import com.example.agritrack.view.login.LoginViewModel
import com.example.agritrack.view.owner.forecasting.ForecastingViewModel
import com.example.agritrack.view.owner.product.ProductViewModel
import com.example.agritrack.view.register.RegisterViewModel

class ViewModelFactory(
    private val authRepository: AuthRepository,
    private val ownerRepository: OwnerRepository,
    private val consumerRepository: ConsumerRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(ProductViewModel::class.java) -> {
                ProductViewModel(ownerRepository) as T
            }
            modelClass.isAssignableFrom(com.example.agritrack.view.consumer.ProductViewModel::class.java) -> {
                com.example.agritrack.view.consumer.ProductViewModel(consumerRepository) as T
            }
            modelClass.isAssignableFrom(ForecastingViewModel::class.java) -> {
                ForecastingViewModel(ownerRepository) as T
            }
            else -> throw IllegalArgumentException("Uknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideAuthRepository(context), Injection.provideOwnerRepository(context), Injection.provideConsumerRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}