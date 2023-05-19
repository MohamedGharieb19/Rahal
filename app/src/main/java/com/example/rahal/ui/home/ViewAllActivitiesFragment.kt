package com.example.rahal.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.rahal.R
import com.example.rahal.adapters.ViewAllAdapter
import com.example.rahal.databinding.FragmentViewAllActivitesBinding
import com.example.rahal.viewModels.ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewAllActivitiesFragment : Fragment() {
    private lateinit var binding:FragmentViewAllActivitesBinding
    private lateinit var title: TextView
    private lateinit var city: TextView
    private lateinit var searchBar:EditText
    private lateinit var backButton:ImageView
    private lateinit var viewAllAdapter: ViewAllAdapter
    private val viewModel: ViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentViewAllActivitesBinding.inflate(inflater,container,false)

        intilaizeVariables()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        fetchData()
        onPlaceClick()

        backButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_viewAllActivitesFragment_to_homePageFragment)
        }

        searchBar.setOnClickListener {
            findNavController().navigate(R.id.action_viewAllActivitesFragment_to_searchFragment)
        }



    }

    private fun intilaizeVariables(){
        backButton = binding.backArrowButton
        title = binding.titleTextView
        city = binding.cityTextView
        searchBar = binding.searchBar
    }

    private fun getData(){
        val data = arguments
        if (data != null){
            title.text = data.getString("title").toString()
            city.text = data.getString("city").toString()
        }
    }

    private fun fetchData(){
        setupRecyclerView()
        if (title.text == "Recommended"){
            if (city.text == "City"){
                viewModel.getRecommended()
                viewModel.getRecommendedLiveData.observe(viewLifecycleOwner, Observer {
                    viewAllAdapter.differ.submitList(it)
                })
            }else{
                viewModel.getRecommendedForSpecificCity(city.text.toString())
                viewModel.getRecommendedForSpecificCityLiveData.observe(viewLifecycleOwner, Observer {
                    viewAllAdapter.differ.submitList(it)
                })
            }
        }else if (title.text == "Top rated places"){
            if (city.text == "City") {
                viewModel.getTopRated()
                viewModel.getTopRatedLiveData.observe(viewLifecycleOwner, Observer {
                    viewAllAdapter.differ.submitList(it)
                })
            }else{
                viewModel.getTopRatedForSpecificCity(city.text.toString())
                viewModel.getTopRatedForSpecificCityLiveData.observe(viewLifecycleOwner, Observer {
                    viewAllAdapter.differ.submitList(it)
                })
            }
        } else {
            if (city.text == "City") {
                viewModel.getContentOfActivities("cairo",title.text.toString())
                viewModel.getContentActivitiesLiveData.observe(viewLifecycleOwner, Observer {
                    viewAllAdapter.differ.submitList(it)
                })
            }else{
                viewModel.getContentOfActivities(city.text.toString(),title.text.toString())
                viewModel.getContentActivitiesLiveData.observe(viewLifecycleOwner, Observer {
                    viewAllAdapter.differ.submitList(it)
                })
            }
        }
    }

    private fun setupRecyclerView(){
        viewAllAdapter = ViewAllAdapter()
        binding.viewAllRecyclerView.apply {
            adapter = viewAllAdapter
        }
    }

    private fun onPlaceClick(){
        viewAllAdapter.onPlaceItemClick = { data ->
            val fragment = ViewPlaceFragment()
            val bundle = Bundle()
            bundle.putString("image",data.image)
            bundle.putDouble("rate",data.rating)
            bundle.putString("title",data.name)
            bundle.putString("reviews",data.num_reviews.toString())
            bundle.putString("reviews",data.numberOfReviews.toString())
            bundle.putString("description",data.description)
            bundle.putString("address",data.location.address)
            bundle.putString("location",data.location.coordiantes.toString())
            fragment.arguments = bundle
            findNavController().navigate(R.id.action_viewAllActivitesFragment_to_viewPlaceFragment,bundle)
        }
    }

}