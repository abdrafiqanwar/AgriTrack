package com.example.agritrack.data.response

import com.google.gson.annotations.SerializedName

data class CommodityTypeResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("productCategory")
	val productCategory: List<CommodityTypeItem> = emptyList()
)

data class CommodityTypeItem(

	@field:SerializedName("commodityType")
	val commodityType: String? = null
)
