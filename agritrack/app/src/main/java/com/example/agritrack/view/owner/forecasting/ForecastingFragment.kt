package com.example.agritrack.view.owner.forecasting

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
import com.google.android.material.snackbar.Snackbar

class ForecastingFragment : Fragment() {

    private lateinit var binding: FragmentForecastingBinding
    private val viewModel by viewModels<ForecastingViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private lateinit var selectedCommodityType: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForecastingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeCommodityTypes()
        setupSubmitButton()
    }

    private fun setupRecyclerView() {
        binding.rvPrediction.layoutManager = LinearLayoutManager(requireActivity())
    }

    private fun observeCommodityTypes() {
        viewModel.getCommodityTypes().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val commodityTypes = result.data.map { it.commodityType.toString() }
                    val adapter = ArrayAdapter(requireActivity(), R.layout.item_dropdown, commodityTypes)
                    binding.category.adapter = adapter
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    showError(result.error)
                }
            }
        }

        binding.category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedCommodityType = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Optional: handle case when nothing is selected
            }
        }
    }

    private fun setupSubmitButton() {
        binding.btnSubmit.setOnClickListener {
            loadPredictions()
        }
    }

    private fun loadPredictions() {
        viewModel.getPrediction(selectedCommodityType).observe(viewLifecycleOwner) { result ->
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