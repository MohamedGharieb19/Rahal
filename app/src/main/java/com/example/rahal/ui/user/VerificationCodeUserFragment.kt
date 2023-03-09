package com.example.rahal.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rahal.R
import com.example.rahal.databinding.FragmentVerificationCodeUserBinding


class VerificationCodeFragment : Fragment() {
    lateinit var binding: FragmentVerificationCodeUserBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVerificationCodeUserBinding.inflate(inflater,container,false)
        return binding.root
    }

}