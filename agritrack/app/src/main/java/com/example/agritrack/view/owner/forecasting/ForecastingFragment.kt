package com.example.agritrack.view.owner.forecasting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.example.agritrack.R
import com.example.agritrack.databinding.FragmentForecastingBinding
import com.example.agritrack.di.Result
import com.example.agritrack.view.ViewModelFactory
import com.example.agritrack.view.owner.product.ProductViewModel

class ForecastingFragment : Fragment() {

    private lateinit var binding: FragmentForecastingBinding
    private val viewModel by viewModels<ForecastingViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentForecastingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var item = ""

        viewModel.getCommodityTypes().observe(requireActivity()) {
            if (it != null) {
                when (it) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        val list = mutableListOf<String>()

                        for (i in it.data) {
                            list.add(i.commodityType.toString())
                        }

                        val arrayAdapter = ArrayAdapter(requireActivity(), R.layout.item_dropdown, list)
                        binding.category.adapter = arrayAdapter
                    }
                    is Result.Error -> {}
                }
            }
        }

        binding.category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                item = p0?.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }
}