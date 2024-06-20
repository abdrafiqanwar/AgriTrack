package com.example.agritrack.data.response

import com.google.gson.annotations.SerializedName

data class AddProductResponse(

	@field:SerializedName("productId")
	val productId: String? = null,

	@field:SerializedName("productComposition")
	val productComposition: String? = null,

	@field:SerializedName("productOrigin")
	val productOrigin: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("nutritionFacts")
	val nutritionFacts: String? = null,

	@field:SerializedName("productName")
	val productName: String? = null,

	@field:SerializedName("productCategory")
	val productCategory: String? = null
)
