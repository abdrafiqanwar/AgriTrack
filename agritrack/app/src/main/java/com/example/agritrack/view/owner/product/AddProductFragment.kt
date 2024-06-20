package com.example.agritrack.view.owner.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.commit
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

        var item = ""

        val fragmentManager = parentFragmentManager
        val productInfoFragment = ProductInfoFragment()

        viewModel.getProductCategory().observe(requireActivity()) {
            if (it != null) {
                when (it) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        val list = mutableListOf<String>()

                        for (i in it.data) {
                            list.add(i.categoryName.toString())
                        }

                        val arrayAdapter = ArrayAdapter(requireActivity(), R.layout.item_dropdown, list)
                        binding.category.adapter = arrayAdapter
                    }
                    is Result.Error -> {
                        Toast.makeText(requireActivity(), it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        binding.category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                item = p0?.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.tvBack.setOnClickListener {
            fragmentManager.commit {
                replace(R.id.frame_container, productInfoFragment, ProductInfoFragment::class.java.simpleName)
            }
        }

        binding.btnSubmit.setOnClickListener {
            val id = binding.etId.text.toString()
            val name = binding.etName.text.toString()
            val origin = binding.etOrigin.text.toString()
            val category = item
            val composition = binding.etComposition.text.toString()
            val nutritionFacts = binding.etNutritionFacts.text.toString()

            if (id.isEmpty()) {
                binding.etId.error = "Please enter you product ID"
            }
            if (name.isEmpty()) {
                binding.etName.error = "Please enter your product name"
            }
            if (origin.isEmpty()) {
                binding.etOrigin.error = "Please enter your product origin"
            }
            if (composition.isEmpty()) {
                binding.etComposition.error = "Please enter your product composition"
            }
            if (nutritionFacts.isEmpty()) {
                binding.etNutritionFacts.error = " Please enter your product "
            }

            if (id.isNotEmpty() && name.isNotEmpty() && origin.isNotEmpty() && composition.isNotEmpty() && nutritionFacts.isNotEmpty()) {
                viewModel.postProduct(id, name, origin, category, composition, nutritionFacts).observe(requireActivity()) {
                    if (it != null) {
                        when (it) {
                            is Result.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                                binding.btnSubmit.visibility = View.GONE
                            }
                            is Result.Success -> {
                                binding.progressBar.visibility = View.GONE
                                binding.btnSubmit.visibility = View.VISIBLE

                                fragmentManager.commit {
                                    replace(R.id.frame_container, productInfoFragment, ProductInfoFragment::class.java.simpleName)
                                }

                                Toast.makeText(requireActivity(), it.data.message, Toast.LENGTH_LONG).show()
                            }
                            is Result.Error -> {
                                binding.progressBar.visibility = View.GONE
                                binding.btnSubmit.visibility = View.VISIBLE

                                Toast.makeText(requireActivity(), it.error, Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            }
        }
    }
}