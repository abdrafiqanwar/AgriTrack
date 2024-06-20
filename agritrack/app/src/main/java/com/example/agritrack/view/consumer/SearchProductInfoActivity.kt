package com.example.agritrack.view.consumer

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.agritrack.data.response.ProductsItem
import com.example.agritrack.databinding.ActivitySearchProductInfoBinding
import com.example.agritrack.di.Result
import com.example.agritrack.view.ViewModelFactory
import com.example.agritrack.view.login.LoginViewModel
import com.example.agritrack.view.login.LoginActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class SearchProductInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchProductInfoBinding
    private val viewModel by viewModels<ProductViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val userViewModel by viewModels<LoginViewModel> {
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
        setupRecyclerView()
        setupLogout()
        setupSearch()

        observeAllProducts()
    }

    private fun setupRecyclerView() {
        binding.rvSearch.layoutManager = LinearLayoutManager(this)
        binding.rvSearch.adapter = ProductAdapter()
    }

    private fun setupLogout() {
        binding.tvLogout.setOnClickListener {
            userViewModel.logout()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setupSearch() {
        binding.etSearchWithID.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchQuery = s.toString().trim()
                if (searchQuery.isEmpty()) {
                    binding.singleProduct.visibility = View.GONE
                    binding.rvSearch.visibility = View.VISIBLE
                    observeAllProducts()
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnSearch.setOnClickListener {
            val searchQuery = binding.etSearchWithID.text.toString().trim()
            if (searchQuery.isEmpty()) {
                binding.etSearchWithID.error = "Please enter the product ID"
            } else {
                searchProductById(searchQuery)
            }
        }
    }

    private fun observeAllProducts() {
        viewModel.getAllProducts().observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvSearch.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvSearch.visibility = View.VISIBLE
                    (binding.rvSearch.adapter as ProductAdapter).submitList(result.data)
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    showAlert(result.error)
                }
            }
        }
    }

    private fun searchProductById(productId: String) {
        viewModel.searchProducts(productId).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvSearch.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvSearch.visibility = View.GONE
                    binding.singleProduct.visibility = View.VISIBLE
                    displayProduct(result.data)
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    showAlert(result.error)
                }
            }
        }
    }

    private fun displayProduct(product: ProductsItem) {
        binding.tvItemName.text = product.productName
        binding.tvDesc.text = product.productCategory
    }

    private fun showAlert(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Error")
            setMessage(message)
            setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }
}