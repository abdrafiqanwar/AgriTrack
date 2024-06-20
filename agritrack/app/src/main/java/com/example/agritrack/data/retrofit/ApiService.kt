package com.example.agritrack.data.retrofit

import com.example.agritrack.data.response.AddProductResponse
import com.example.agritrack.data.response.LoginResponse
import com.example.agritrack.data.response.ProductCategoryResponse
import com.example.agritrack.data.response.ProductResponse
import com.example.agritrack.data.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("auth/signup")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("role") role: String
    ) : RegisterResponse

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : LoginResponse

    @GET("products/get-user-products")
    suspend fun getUserProducts() : ProductResponse

    @GET("products/get-products-categories")
    suspend fun getProductCategories() : ProductCategoryResponse

    @FormUrlEncoded
    @POST("products/post-products")
    suspend fun postProduct(
        @Field("productId") productId: String,
        @Field("productName") productName: String,
        @Field("productOrigin") productOrigin: String,
        @Field("productCategory") productCategory: String,
        @Field("productComposition") productComposition: String,
        @Field("nutritionFacts") nutritionFacts: String
    ) : AddProductResponse
}