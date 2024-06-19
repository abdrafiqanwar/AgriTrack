package com.example.agritrack.view.owner.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.agritrack.R
import com.example.agritrack.databinding.FragmentEditProductBinding

class EditProductFragment : Fragment() {

    private lateinit var binding: FragmentEditProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditProductBinding.inflate(inflater, container, false)
        return binding.root
    }
}