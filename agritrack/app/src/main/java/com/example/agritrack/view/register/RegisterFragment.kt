package com.example.agritrack.view.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import com.example.agritrack.R
import com.example.agritrack.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentManager = parentFragmentManager
        val homeFragment = HomeFragment()

        binding.btnBack.setOnClickListener{
            fragmentManager.commit {
                replace(R.id.frame_container, homeFragment, HomeFragment::class.java.simpleName)
            }
        }

        binding.tvLogin.setOnClickListener{ requireActivity().finish() }

        if (arguments != null) {
            val user = arguments?.getString("user")
            binding.tvUser.text = user
        }
    }
}