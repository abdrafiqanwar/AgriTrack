package com.example.agritrack.view.owner.forecasting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.agritrack.databinding.ItemPredictionBinding
import java.text.NumberFormat
import java.util.*

class ForecastingAdapter(private val predictions: List<Double>) :
    RecyclerView.Adapter<ForecastingAdapter.PredictionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PredictionViewHolder {
        val binding = ItemPredictionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PredictionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PredictionViewHolder, position: Int) {
        holder.bind(predictions[position], position + 1)
    }

    override fun getItemCount(): Int = predictions.size

    inner class PredictionViewHolder(private val binding: ItemPredictionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(prediction: Double, day: Int) {
            val formattedPrice = formatPrice(prediction)
            binding.tvDay.text = "Day $day"
            binding.tvPrediction.text = formattedPrice
        }

        private fun formatPrice(value: Double): String {
            val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            format.maximumFractionDigits = 3
            return format.format(value)
        }
    }
}
