package com.example.agritrack.view.owner.forecasting

import android.content.Intent
import android.widget.Toast
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agritrack.R
import com.example.agritrack.databinding.FragmentForecastingBinding
import com.example.agritrack.di.Result
import com.example.agritrack.view.ViewModelFactory
import com.example.agritrack.view.login.LoginActivity
import com.example.agritrack.view.login.LoginViewModel
import com.google.android.material.snackbar.Snackbar

class ForecastingFragment : Fragment() {

    private lateinit var binding: FragmentForecastingBinding
    private val viewModel by viewModels<ForecastingViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }
    private val userViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentForecastingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var selectedItem = ""

        viewModel.getCommodityTypes().observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> {
                    binding.pbCategory.visibility = View.VISIBLE
                    binding.category.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.pbCategory.visibility = View.GONE
                    binding.category.visibility = View.VISIBLE

                    val list = it.data.map { commodity -> commodity.commodityType.toString() }

                    val arrayAdapter = ArrayAdapter(requireActivity(), R.layout.item_dropdown, list)
                    binding.category.adapter = arrayAdapter
                }
                is Result.Error -> {
                    binding.pbCategory.visibility = View.GONE
                    binding.category.visibility = View.VISIBLE

                    Toast.makeText(requireActivity(), it.error, Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedItem = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Optional: handle case when nothing is selected
            }
        }

        binding.tvLogout.setOnClickListener {
            userViewModel.logout()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        binding.btnSubmit.setOnClickListener {
            loadPredictions(selectedItem)
        }

        // Set the LayoutManager for the RecyclerView
        binding.rvPrediction.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun loadPredictions(item: String) {
        viewModel.getPrediction(item).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvPrediction.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvPrediction.visibility = View.VISIBLE
                    val predictions = result.data
                    binding.rvPrediction.adapter = ForecastingAdapter(predictions)
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    showError(result.error)
                }
            }
        }
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}