package com.example.agritrack.data.response

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("predictionData")
	val predictionData: List<Double>
)
