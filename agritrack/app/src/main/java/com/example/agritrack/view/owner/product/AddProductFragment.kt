package com.example.agritrack.view.owner.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.agritrack.R
import com.example.agritrack.databinding.FragmentAddProductBinding
import com.example.agritrack.di.Result
import com.example.agritrack.view.ViewModelFactory
import com.google.gson.internal.bind.ArrayTypeAdapter

class AddProductFragment : Fragment() {

    private lateinit var binding: FragmentAddProductBinding
    private val viewModel by viewModels<ProductViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProductCategory().observe(requireActivity()) {
            if (it != null) {
                when (it) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        val list = mutableListOf<String>()

                        for (i in it.data) {
                            list.add(i.categoryName.toString())
                        }
                        println(list)
                        val arrayAdapter = ArrayAdapter(requireActivity(), R.layout.item_dropdown, list)
                        binding.category.adapter = arrayAdapter
                    }
                    is Result.Error -> {}
                }
            }
        }
    }
}