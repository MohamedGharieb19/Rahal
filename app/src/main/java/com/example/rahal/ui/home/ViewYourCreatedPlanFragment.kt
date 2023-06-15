package com.example.rahal.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.rahal.R
import com.example.rahal.adapters.PlacesInCreatedPlanAdapter
import com.example.rahal.data.createPlans.PlacesInCreatedPlan
import com.example.rahal.databinding.FragmentViewYourCreatedPlanBinding
import com.example.rahal.viewModels.ViewModel


class ViewYourCreatedPlanFragment : Fragment() {
    private lateinit var binding: FragmentViewYourCreatedPlanBinding
    private val viewModel : ViewModel by viewModels()
    private lateinit var placesInCreatedPlanAdapter: PlacesInCreatedPlanAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentViewYourCreatedPlanBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getYourPlans()
        onPlaceInPlanClick()

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_viewYourCreatedPlanFragment_to_searchFragment)
        }
    }

    private fun getYourPlans(){
        setupRecyclerView()
        val placesList = arguments?.getParcelableArrayList<PlacesInCreatedPlan>("list")
        placesInCreatedPlanAdapter.differ.submitList(placesList)


    }

    private fun setupRecyclerView() {
        placesInCreatedPlanAdapter = PlacesInCreatedPlanAdapter()
        binding.recyclerView.apply {
            adapter = placesInCreatedPlanAdapter
        }
    }

    private fun onPlaceInPlanClick(){
        placesInCreatedPlanAdapter.onPlaceInPlanClick = { data ->
            val fragment = ViewPlaceFragment()
            val bundle = Bundle()
            bundle.putString("image",data.image)
            bundle.putDouble("rate",data.rating)
            bundle.putString("title",data.name)
            bundle.putString("reviews",data.numberOfReviews.toString())
            bundle.putString("description",data.description)
            //bundle.putString("address",data.location.address)
            //bundle.putString("location",data.location.coordinates.toString())
            fragment.arguments = bundle
            findNavController().navigate(R.id.action_viewYourCreatedPlanFragment_to_viewPlaceFragment,bundle)
        }
    }
}