package com.example.rahal.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import com.example.rahal.R
import com.example.rahal.databinding.FragmentReviewBinding


class ReviewFragment : Fragment() {
    private lateinit var binding:FragmentReviewBinding
    private lateinit var backArrowButton:ImageView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentReviewBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backArrowButton = binding.backArrowButton

        backArrowButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_reviewFragment_to_viewPlaceFragment)
        }

    }

}