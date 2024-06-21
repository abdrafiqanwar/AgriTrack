package com.example.agritrack.view.consumer

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.agritrack.R
import com.example.agritrack.data.response.ProductsItem
import com.example.agritrack.databinding.ActivityDetailProductBinding

class DetailProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val info = intent.getParcelableExtra<ProductsItem>("infoProduct")
        if (info != null) {
            binding.etId.text = info.productId
            binding.tvProductName.text = info.productName
            binding.etOrigin.text = info.productOrigin
            binding.etComposition.text = info.productComposition
            binding.etCategory.text = info.productCategory
            binding.etNutritionFacts.text = info.nutritionFacts
        }

    }
}