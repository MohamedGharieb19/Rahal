package com.example.rahal.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.rahal.R
import com.example.rahal.adapters.RecommendedAdapter
import com.example.rahal.adapters.placesInPlanAdapter
import com.example.rahal.data.createPlans.PlacesInCreatedPlan
import com.example.rahal.data.suggestedPlans.PlaceInPlan
import com.example.rahal.databinding.FragmentViewPlanBinding
import com.example.rahal.viewModels.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class ViewPlanFragment : Fragment() {
    private lateinit var binding:FragmentViewPlanBinding
    private val viewModel : ViewModel by viewModels()
    private lateinit var placesInPlanAdapter: placesInPlanAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentViewPlanBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRecommendedPlans()
        onPlaceInPlanClick()
    }

    private fun getRecommendedPlans(){
        setupRecyclerView()
        val placesList = arguments?.getParcelableArrayList<PlaceInPlan>("list")
        viewModel.getRecommendedPlanss()
        viewModel.getRecommendedViewPlansLiveData.observe(viewLifecycleOwner, Observer {
            placesInPlanAdapter.differ.submitList(placesList)
        })
    }

    private fun setupRecyclerView() {
        placesInPlanAdapter = placesInPlanAdapter()
        binding.recyclerView.apply {
            adapter = placesInPlanAdapter
        }
    }

    private fun onPlaceInPlanClick(){
        placesInPlanAdapter.onPlaceInPlanClick = { data ->
            val fragment = ViewPlaceFragment()
            val bundle = Bundle()
            bundle.putString("image",data.image)
            bundle.putDouble("rate",data.rating)
            bundle.putString("title",data.name)
            bundle.putString("reviews",data.numberOfReviews.toString())
            bundle.putString("description",data.description)
            bundle.putString("address",data.location.address)
            bundle.putString("location",data.location.coordinates.toString())
            fragment.arguments = bundle
            findNavController().navigate(R.id.action_planFragment_to_viewPlaceFragment,bundle)
        }
    }


}