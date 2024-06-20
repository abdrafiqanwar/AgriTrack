package com.example.agritrack.view.consumer

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agritrack.R
import com.example.agritrack.databinding.ActivitySearchProductInfoBinding
import com.example.agritrack.di.Result
import com.example.agritrack.view.ViewModelFactory

class SearchProductInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchProductInfoBinding
    private val viewModel by viewModels<ProductViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySearchProductInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.rvSearch.layoutManager = LinearLayoutManager(this)
        val adapter = ProductAdapter()
        binding.rvSearch.adapter = adapter

        binding.btnSearch.setOnClickListener {

        }

        viewModel.getAllProducts().observe(this) {
            if (it != null) {
                when (it) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE

                        adapter.submitList(it.data)
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE

                        AlertDialog.Builder(this).apply {
                            setTitle(it.error)
                            setPositiveButton("Ok") { dialog, _ ->
                                dialog.dismiss()
                            }
                            create()
                            show()
                        }
                    }
                }
            }
        }
    }
}