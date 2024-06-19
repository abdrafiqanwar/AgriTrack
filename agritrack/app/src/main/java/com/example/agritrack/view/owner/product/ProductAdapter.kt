package com.example.agritrack.view.owner.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agritrack.data.response.ProductsItem
import com.example.agritrack.databinding.ItemProductsBinding

class ProductAdapter : ListAdapter<ProductsItem, ProductAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder(val binding: ItemProductsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val products = getItem(position)
        Glide.with(holder.itemView.context)
            .load(products.image)
            .into(holder.binding.ivItemProduct)
        holder.binding.tvItemName.text = products.productName
        holder.binding.tvDesc.text = products.productCategory
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductsItem>() {
            override fun areItemsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}