package com.example.agritrack.view.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import com.example.agritrack.R
import com.example.agritrack.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvLogin.setOnClickListener{ requireActivity().finish() }

        val fragmentManager = parentFragmentManager
        val registerFragment = RegisterFragment()

        val bundle = Bundle()

        binding.btnOwner.setOnClickListener{
            bundle.putString("user", "Business Owner")
            registerFragment.arguments = bundle
            fragmentManager.commit {
                addToBackStack(null)
                replace(R.id.frame_container, registerFragment, RegisterFragment::class.java.simpleName)
            }
        }

        binding.btnConsumer.setOnClickListener{
            bundle.putString("user", "Consumer")
            registerFragment.arguments = bundle
            fragmentManager.commit {
                addToBackStack(null)
                replace(R.id.frame_container, registerFragment, RegisterFragment::class.java.simpleName)
            }
        }
    }
}