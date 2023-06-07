package com.example.rahal.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.rahal.R
import com.example.rahal.adapters.ActivitiesAdapter
import com.example.rahal.adapters.RecommendedTopRatedAdapter
import com.example.rahal.databinding.FragmentHomePageBinding
import com.example.rahal.viewModels.ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageFragment : Fragment() {
    private lateinit var binding: FragmentHomePageBinding
    private lateinit var helpIcon:ImageView
    private lateinit var recommendedViewAll:TextView
    private lateinit var topRatedViewAll:TextView
    private lateinit var searchBar: EditText
    private lateinit var setCity: TextView
    private val viewModel: ViewModel by viewModels()
    private lateinit var recommendedAdapter : RecommendedTopRatedAdapter
    private lateinit var topRatedAdapter: RecommendedTopRatedAdapter
    private lateinit var activitiesAdapter:ActivitiesAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomePageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        intilaizeVariables()
        getRecommended()
        getTopRated()
        onPlaceRecommendedClick()
        onPlaceTopRatedClick()
        onFavoritesIconClick()
        getActivities()
        onActivityClick()

        helpIcon.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homePageFragment_to_FAQFragment)
        }

        recommendedViewAll.setOnClickListener {
            onViewAllRecommendedClick()
        }

        topRatedViewAll.setOnClickListener {
            onViewAllTopRatedClick()
        }

        searchBar.setOnClickListener {
            findNavController().navigate(R.id.action_homePageFragment_to_searchFragment)
        }

        setCity.setOnClickListener {
            showPopupMenu()
        }

        setCity.doAfterTextChanged {
            getRecommendedForSpecificCity(setCity.text.toString())
            getTopRatedForSpecificCity(setCity.text.toString())
            onPlaceRecommendedClick()
            onPlaceTopRatedClick()
            onFavoritesIconClick()

        }


    }

    private fun intilaizeVariables(){
        recommendedViewAll = binding.recommendedViewAll
        topRatedViewAll = binding.topRatedViewAll
        helpIcon = binding.helpIcon
        searchBar = binding.searchBar
        setCity = binding.cityTextView
    }
    private fun setupRecommendedRecyclerView(){
        recommendedAdapter = RecommendedTopRatedAdapter()
        binding.recommendedRecyclerView.apply {
            adapter = recommendedAdapter
        }
    }
    private fun setupTopRatedRecyclerView(){
        topRatedAdapter = RecommendedTopRatedAdapter()
        binding.topRatedRecyclerView.apply {
            adapter = topRatedAdapter
        }
    }
    private fun getActivities(){
        setupActivitiesRecyclerView()
        viewModel.getActivities("cairo")
        viewModel.getActivitiesLiveData.observe(viewLifecycleOwner, Observer {
            activitiesAdapter.differ.submitList(it)
        })
    }
    private fun setupActivitiesRecyclerView(){
        activitiesAdapter = ActivitiesAdapter()
        binding.favoriteRecyclerView.apply {
            adapter = activitiesAdapter
        }
    }
    private fun getTopRated(){
        setupTopRatedRecyclerView()
        viewModel.getTopRated("10")
        viewModel.getTopRatedLiveData.observe(viewLifecycleOwner, Observer {
            topRatedAdapter.differ.submitList(it)
        })
    }
    private fun getRecommended(){
        setupRecommendedRecyclerView()
        viewModel.getRecommended("10")
        viewModel.getRecommendedLiveData.observe(viewLifecycleOwner, Observer {
            recommendedAdapter.differ.submitList(it)
        })
    }

    private fun getRecommendedForSpecificCity(cityName: String){
        setupRecommendedRecyclerView()
        viewModel.getRecommendedForSpecificCity(cityName)

        viewModel.getRecommendedLiveData.observe(viewLifecycleOwner, Observer {
            recommendedAdapter.differ.submitList(null)
        })

        viewModel.getRecommendedForSpecificCityLiveData.observe(viewLifecycleOwner, Observer {
            recommendedAdapter.differ.submitList(it)
        })
    }

    private fun getTopRatedForSpecificCity(cityName: String){
        setupTopRatedRecyclerView()
        viewModel.getTopRatedForSpecificCity(cityName)

        viewModel.getTopRatedLiveData.observe(viewLifecycleOwner, Observer {
            topRatedAdapter.differ.submitList(null)
        })

        viewModel.getTopRatedForSpecificCityLiveData.observe(viewLifecycleOwner, Observer {
            topRatedAdapter.differ.submitList(it)
        })
    }


    private fun onActivityClick(){
        activitiesAdapter.onActivityItemClick = {
            val fragment = ViewAllActivitiesFragment()
            val bundle = Bundle()
            bundle.putString("title",it)
            bundle.putString("city",binding.cityTextView.text.toString())
            fragment.arguments = bundle
            findNavController().navigate(R.id.action_homePageFragment_to_viewAllActivitesFragment,bundle)
                .toString()
        }
    }
    private fun onViewAllRecommendedClick(){
        val fragment = ViewAllActivitiesFragment()
        val bundle = Bundle()
        bundle.putString("title",binding.recommendedTextView.text.toString())
        bundle.putString("city",binding.cityTextView.text.toString())
        fragment.arguments = bundle
        findNavController().navigate(R.id.action_homePageFragment_to_viewAllActivitesFragment,bundle)
    }
    private fun onViewAllTopRatedClick(){
        val fragment = ViewAllActivitiesFragment()
        val bundle = Bundle()
        bundle.putString("title",binding.topRatedTextView.text.toString())
        bundle.putString("city",binding.cityTextView.text.toString())
        fragment.arguments = bundle
        findNavController().navigate(R.id.action_homePageFragment_to_viewAllActivitesFragment,bundle)
    }
    private fun onFavoritesIconClick(){
        recommendedAdapter.onFavoritesIconClick = { data ->
                viewModel.upsert(data)
        }
        topRatedAdapter.onFavoritesIconClick = { data ->
            viewModel.upsert(data)
        }
    }
    private fun onPlaceRecommendedClick(){
        recommendedAdapter.onPlaceItemClick = { data ->
            val fragment = ViewPlaceFragment()
            val bundle = Bundle()
            bundle.putString("image",data.image)
            bundle.putDouble("rate",data.rating)
            bundle.putString("title",data.name)
            bundle.putString("reviews",data.num_reviews.toString())
            bundle.putString("reviews",data.numberOfReviews.toString())
            bundle.putString("description",data.description)
            bundle.putString("address",data.location.address)
            //bundle.putString("location",data.location.coordiantes.toString())
            fragment.arguments = bundle
            findNavController().navigate(R.id.action_homePageFragment_to_viewPlaceFragment,bundle)
        }
    }
    private fun onPlaceTopRatedClick(){
        topRatedAdapter.onPlaceItemClick = { data ->
            val fragment = ViewPlaceFragment()
            val bundle = Bundle()
            bundle.putString("image",data.image)
            bundle.putDouble("rate",data.rating)
            bundle.putString("title",data.name)
            bundle.putString("reviews",data.num_reviews.toString())
            bundle.putString("reviews",data.numberOfReviews.toString())
            bundle.putString("description",data.description)
            bundle.putString("address",data.location.address)
            //bundle.putString("location",data.location.coordiantes.toString())
            fragment.arguments = bundle
            findNavController().navigate(R.id.action_homePageFragment_to_viewPlaceFragment,bundle)
        }
    }
    private fun showPopupMenu() {
        val popupMenu = PopupMenu(requireContext(),binding.locationIc)
        popupMenu.menuInflater.inflate(R.menu.cites_name, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            setCity.text = menuItem.title.toString()
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

}




