package com.example.agritrack.view.owner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.agritrack.R
import com.example.agritrack.databinding.FragmentForecastingBinding

class ForecastingFragment : Fragment() {

    private lateinit var binding: FragmentForecastingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentForecastingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}