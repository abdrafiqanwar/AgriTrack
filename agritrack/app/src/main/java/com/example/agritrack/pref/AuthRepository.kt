package com.example.agritrack.pref

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.agritrack.data.response.LoginResponse
import com.example.agritrack.data.response.RegisterResponse
import com.example.agritrack.data.retrofit.ApiService
import com.example.agritrack.di.Result
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class AuthRepository private constructor(
    private var apiService: ApiService,
    private val authPreference: AuthPreference
){
    fun register(name: String, email: String, password: String, role: String): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.register(name, email, password, role)

            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
            val errorMessage = errorBody.message

            emit(Result.Error(errorMessage.toString()))
        }
    }

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.login(email, password)

            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, LoginResponse::class.java)
            val errorMessage = errorBody.message

            emit(Result.Error(errorMessage.toString()))
        }
    }

    suspend fun saveSession(user: UserModel) {
        authPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> = authPreference.getSession()

    suspend fun logout() {
        authPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: AuthRepository? = null
        fun getInstance(
            apiService: ApiService,
            authPreference: AuthPreference
        ): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(apiService, authPreference)
            }.also { instance = it }
    }
}