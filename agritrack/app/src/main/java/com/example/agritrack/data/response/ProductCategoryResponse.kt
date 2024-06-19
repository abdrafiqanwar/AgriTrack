package com.example.agritrack.data.response

import com.google.gson.annotations.SerializedName

data class ProductCategoryResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("productCategory")
	val productCategory: List<ProductCategoryItem> = emptyList()
)

data class ProductCategoryItem(

	@field:SerializedName("category_name")
	val categoryName: String? = null
)
