package com.example.agritrack.view.owner.product

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agritrack.R
import com.example.agritrack.data.response.ProductsItem
import com.example.agritrack.databinding.FragmentProductInfoBinding
import com.example.agritrack.di.Result
import com.example.agritrack.view.ViewModelFactory
import com.example.agritrack.view.login.LoginActivity
import com.example.agritrack.view.login.LoginViewModel

class ProductInfoFragment : Fragment() {

    private lateinit var binding: FragmentProductInfoBinding
    private val viewModel by viewModels<ProductViewModel> {
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
        binding = FragmentProductInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvItemProducts.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = ProductAdapter()
        binding.rvItemProducts.adapter = adapter
        val listProduct = mutableListOf<ProductsItem>()

        val fragmentManager = parentFragmentManager
        val addProductFragment = AddProductFragment()
        val editProductFragment = EditProductFragment()

        val bundle = Bundle()

        binding.tvLogout.setOnClickListener{
            userViewModel.logout()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        binding.addProduct.setOnClickListener {
            fragmentManager.commit {
                addToBackStack(null)
                replace(R.id.frame_container, addProductFragment, AddProductFragment::class.java.simpleName)
            }
        }

        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                adapter.submitList(listProduct.filter {
                    it.productName!!.lowercase().contains(binding.searchView.text.toString())
                })
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        viewModel.getUserProducts().observe(requireActivity()) {
            if (it != null) {
                when (it) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE

                        adapter.submitList(it.data)

                        adapter.setOnItemClickCallback(object : ProductAdapter.OnItemClickCallback {
                            override fun onItemClicked(data: ProductsItem) {
                                bundle.putParcelable("data", data)
                                editProductFragment.arguments = bundle
                                fragmentManager.commit {
                                    addToBackStack(null)
                                    replace(R.id.frame_container,editProductFragment, EditProductFragment::class.java.simpleName)
                                }
                            }
                        })

                        for (i in it.data) {
                            listProduct.add(i)
                        }
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE

                        Toast.makeText(requireActivity(), it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}