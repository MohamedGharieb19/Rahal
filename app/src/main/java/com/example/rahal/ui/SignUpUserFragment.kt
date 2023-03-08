package com.example.rahal.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.rahal.R
import com.example.rahal.databinding.FragmentSignUpUserBinding


class SignUpUserFragment : Fragment() {
    lateinit var binding: FragmentSignUpUserBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpUserBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backArrowButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.logInUserFragment)
        }

        binding.logInTextVeiw.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.logInUserFragment)
        }

        binding.floatingButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.followSignUpUserFragment)
        }


    }

}