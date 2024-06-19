package com.example.agritrack.pref

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.agritrack.data.response.LoginResponse
import com.example.agritrack.data.response.ProductCategoryItem
import com.example.agritrack.data.response.ProductResponse
import com.example.agritrack.data.response.ProductsItem
import com.example.agritrack.data.retrofit.ApiConfig
import com.example.agritrack.data.retrofit.ApiService
import com.example.agritrack.di.Result
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import retrofit2.HttpException

class OwnerRepository private constructor(
    private var apiService: ApiService,
    private val authPreference: AuthPreference
){

    fun getUserProducts(): LiveData<Result<List<ProductsItem>>> = liveData {
        emit(Result.Loading)

        try {
            apiService = ApiConfig.getApiService(authPreference.getSession().first().token)
            val response = apiService.getUserProducts()
            val data = response.products

            emit(Result.Success(data))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ProductResponse::class.java)
            val errorMessage = errorBody.message

            emit(Result.Error(errorMessage.toString()))
        }
    }

    fun getProductCategory(): LiveData<Result<List<ProductCategoryItem>>> = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.getProductCategories()
            val data = response.productCategory

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
        private var instance: OwnerRepository? = null
        fun getInstance(
            apiService: ApiService,
            authPreference: AuthPreference
        ): OwnerRepository =
            instance ?: synchronized(this) {
                instance ?: OwnerRepository(apiService, authPreference)
            }.also { instance = it }
    }
}