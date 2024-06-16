package com.example.agritrack.view.owner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import com.example.agritrack.R
import com.example.agritrack.databinding.FragmentProductInfoBinding

class ProductInfoFragment : Fragment() {

    private lateinit var binding: FragmentProductInfoBinding

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

        val fragmentManager = parentFragmentManager
        val addProductFragment = AddProductFragment()

        binding.addProduct.setOnClickListener {
            fragmentManager.commit {
                addToBackStack(null)
                replace(R.id.frame_container, addProductFragment, AddProductFragment::class.java.simpleName)
            }
        }
    }
}