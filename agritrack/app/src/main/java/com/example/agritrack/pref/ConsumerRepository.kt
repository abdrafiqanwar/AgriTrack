package com.example.agritrack.pref

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.agritrack.data.response.ProductResponse
import com.example.agritrack.data.response.ProductsItem
import com.example.agritrack.data.retrofit.ApiConfig
import com.example.agritrack.data.retrofit.ApiService
import com.example.agritrack.di.Result
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import retrofit2.HttpException

class ConsumerRepository private constructor(
    private var apiService: ApiService,
    private var authPreference: AuthPreference
) {

    fun getAllProducts(): LiveData<Result<List<ProductsItem>>> = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.getAllProducts()
            val data = response.products

            emit(Result.Success(data))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ProductResponse::class.java)
            val errorMessage = errorBody.message

            emit(Result.Error(errorMessage.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: ConsumerRepository? = null
        fun getInstance(
            apiService: ApiService,
            authPreference: AuthPreference
        ): ConsumerRepository =
            instance ?: synchronized(this) {
                instance ?: ConsumerRepository(apiService, authPreference)
            }.also { instance = it }
    }
}