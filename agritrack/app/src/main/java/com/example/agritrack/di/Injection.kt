package com.example.agritrack.di

import android.content.Context
import com.example.agritrack.data.retrofit.ApiConfig
import com.example.agritrack.pref.AuthPreference
import com.example.agritrack.pref.AuthRepository
import com.example.agritrack.pref.OwnerRepository
import com.example.agritrack.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideAuthRepository(context: Context) : AuthRepository {
        val pref = AuthPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return AuthRepository.getInstance(apiService, pref)
    }

    fun provideOwnerRepository(context: Context) : OwnerRepository {
        val pref = AuthPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return OwnerRepository.getInstance(apiService, pref)
    }
}