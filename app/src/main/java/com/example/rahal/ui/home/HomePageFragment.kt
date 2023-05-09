package com.example.rahal.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.rahal.R
import com.example.rahal.adapters.ActivitiesAdapter
import com.example.rahal.adapters.RecommendedTopRatedAdapter
import com.example.rahal.databinding.CustomRectangleItemForRecylerViewBinding
import com.example.rahal.databinding.FragmentHomePageBinding
import com.example.rahal.remove.Circle
import com.example.rahal.remove.CircleAdapter
import com.example.rahal.viewModels.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

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

        viewModel.getContentOfActivities("cairo","Shopping")


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
            viewModel.getNew(setCity.text.toString())
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
            Log.d("testApp","homepage" + it)
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
        viewModel.getTopRated()
        viewModel.getTopRatedLiveData.observe(viewLifecycleOwner, Observer {
            topRatedAdapter.differ.submitList(it)
        })
    }
    private fun getRecommended(){
        setupRecommendedRecyclerView()
        viewModel.getRecommended()
        viewModel.getRecommendedLiveData.observe(viewLifecycleOwner, Observer {
            recommendedAdapter.differ.submitList(it)
        })
    }
    private fun onActivityClick(){
        activitiesAdapter.onActivityItemClick = {
            val fragment = ViewAllActivitiesFragment()
            val bundle = Bundle()
            bundle.putString("title",it)
            fragment.arguments = bundle
            findNavController().navigate(R.id.action_homePageFragment_to_viewAllActivitesFragment,bundle)
                .toString()
        }
    }
    private fun onViewAllRecommendedClick(){
        val fragment = ViewAllActivitiesFragment()
        val bundle = Bundle()
        bundle.putString("title",binding.recommendedTextView.text.toString())
        fragment.arguments = bundle
        findNavController().navigate(R.id.action_homePageFragment_to_viewAllActivitesFragment,bundle)
    }
    private fun onViewAllTopRatedClick(){
        val fragment = ViewAllActivitiesFragment()
        val bundle = Bundle()
        bundle.putString("title",binding.topRatedTextView.text.toString())
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
            bundle.putString("description",data.description)
            bundle.putString("address",data.location.address)
            bundle.putString("location",data.location.coordiantes.toString())
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
            bundle.putString("description",data.description)
            bundle.putString("address",data.location.address)
            bundle.putString("location",data.location.coordiantes.toString())
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




