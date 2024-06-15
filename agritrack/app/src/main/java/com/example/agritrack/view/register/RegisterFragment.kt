package com.example.agritrack.view.register

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.example.agritrack.R
import com.example.agritrack.databinding.FragmentRegisterBinding
import com.example.agritrack.di.Result
import com.example.agritrack.view.ViewModelFactory

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

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
        var user = ""

        binding.btnBack.setOnClickListener{
            fragmentManager.commit {
                replace(R.id.frame_container, homeFragment, HomeFragment::class.java.simpleName)
            }
        }

        binding.tvLogin.setOnClickListener{ requireActivity().finish() }

        if (arguments != null) {
        user = arguments?.getString("user").toString()
            binding.tvUser.text = user
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (name.isEmpty()) {
                binding.etName.error = "Please enter your name"
            } else if (password != confirmPassword) {
                binding.etConfirmPassword.error = "The password confirmation does not match"
            } else {
                viewModel.register(name, email, password, user).observe(requireActivity()) {
                    if (it != null) {
                        when(it) {
                            is Result.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is Result.Success -> {
                                binding.progressBar.visibility = View.GONE

                                AlertDialog.Builder(requireActivity()).apply {
                                    setTitle("Registration success")
                                    setPositiveButton("Login") { _, _ ->
                                        requireActivity().finish()
                                    }
                                    create()
                                    show()
                                }
                            }
                            is Result.Error -> {
                                binding.progressBar.visibility = View.GONE

                                AlertDialog.Builder(requireActivity()).apply {
                                    setTitle(it.error)
                                    setPositiveButton("Ok") { dialog, _ ->
                                        dialog.dismiss()
                                    }
                                    create()
                                    show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}