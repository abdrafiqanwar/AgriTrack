package com.example.agritrack.pref

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.agritrack.data.response.AddProductResponse
import com.example.agritrack.data.response.CommodityTypeItem
import com.example.agritrack.data.response.EditProductResponse
import com.example.agritrack.data.response.LoginResponse
import com.example.agritrack.data.response.PredictResponse
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

    fun postProduct(productId: String, productName: String, productOrigin: String, productCategory: String, productComposition: String, nutritionFacts: String): LiveData<Result<AddProductResponse>> = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.postProduct(productId, productName, productOrigin, productCategory, productComposition, nutritionFacts)

            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, AddProductResponse::class.java)
            val errorMessage = errorBody.message

            emit(Result.Error(errorMessage.toString()))
        }
    }

    fun editProduct(productId: String, productName: String, productOrigin: String, productCategory: String, productComposition: String, nutritionFacts: String): LiveData<Result<EditProductResponse>> = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.editProduct(productId, productName, productOrigin, productCategory, productComposition, nutritionFacts)

            emit(Result.Success(response))
        } catch (e:HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, AddProductResponse::class.java)
            val errorMessage = errorBody.message

            emit(Result.Error(errorMessage.toString()))
        }
    }

    fun getCommodityTypes(): LiveData<Result<List<CommodityTypeItem>>> = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.getCommodityTypes()
            val data = response.productCategory

            emit(Result.Success(data))
        } catch (e:HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, AddProductResponse::class.java)
            val errorMessage = errorBody.message

            emit(Result.Error(errorMessage.toString()))
        }
    }

    fun getPrediction(commodityType: String): LiveData<Result<List<Double>>> = liveData {
        emit(Result.Loading)

        try {
            val requestBody = mapOf("commodityType" to commodityType)
            val response = apiService.getPrediction(requestBody)
            val predictionData = response.predictionData
            if (predictionData != null) {
                Log.d("OwnerRepository", "Prediction data received: $predictionData")
                emit(Result.Success(predictionData))
            } else {
                Log.e("OwnerRepository", "Prediction data is null")
                emit(Result.Error("Prediction data is null"))
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, PredictResponse::class.java)
            val errorMessage = errorBody.toString()
            Log.e("OwnerRepository", "HTTP Exception: $errorMessage")
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            Log.e("OwnerRepository", "Exception: ${e.message}")
            emit(Result.Error(e.message ?: "Unknown error"))
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