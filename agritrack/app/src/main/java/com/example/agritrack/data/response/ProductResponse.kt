package com.example.agritrack.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ProductResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("products")
	val products: List<ProductsItem> = emptyList()
)

@Parcelize
data class ProductsItem(

	@field:SerializedName("product_composition")
	val productComposition: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("product_id")
	val productId: String? = null,

	@field:SerializedName("product_origin")
	val productOrigin: String? = null,

	@field:SerializedName("nutrition_facts")
	val nutritionFacts: String? = null,

	@field:SerializedName("product_name")
	val productName: String? = null,

	@field:SerializedName("product_category")
	val productCategory: String? = null
) : Parcelable
