package com.example.rahal.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rahal.R
import com.example.rahal.adapters.CreatedPlansAdapter
import com.example.rahal.data.createPlans.CreatedPlan
import com.example.rahal.data.createPlans.PlacesInCreatedPlan
import com.example.rahal.databinding.FragmentAddToCreatedPlansBinding
import com.example.rahal.viewModels.ViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddToCreatedPlansFragment : Fragment() {
    private lateinit var binding: FragmentAddToCreatedPlansBinding
    private val viewModel: ViewModel by viewModels()
    private lateinit var createdPlansAdapter: CreatedPlansAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddToCreatedPlansBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCreatedPlans()
        getData()

    }

    private fun setupRecyclerView(){
        createdPlansAdapter = CreatedPlansAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = createdPlansAdapter
        }
    }

    private fun getCreatedPlans(){
        setupRecyclerView()
        viewModel.getCreatedPlans().observe(viewLifecycleOwner, Observer { data ->
            if (data.isEmpty()){
                createdPlansAdapter.differ.submitList(null)
            }else {
                createdPlansAdapter.differ.submitList(data)
            }
        })
    }

    fun getData(){
        val data = arguments
        if (data != null){
            val title = data.getString("title").toString()
            val ratingBar = data.getDouble("rate").toFloat()
            val reviews = data.getString("reviews").toString()
            var description = data.getString("description").toString()
            val location = data.getString("address").toString()
            val image = data.getString("image").toString()
            val cordinates = data.getString("location").toString()
            Log.d("testApp",cordinates)
            if (description.equals("null") || description.equals("")){
                description = "Welcome to our place in downtown. Enjoy comfort and convenience with amenities like a fitness center, business center, and on-site restaurant. Our modern rooms feature free Wi-Fi, plush bedding, and 24/7 service. Our friendly staff is available to help make your stay unforgettable. Book now to experience the best of our city from your home away from home."
            }

            createdPlansAdapter.onPlanItemClick = { it ->
                var place = PlacesInCreatedPlan(0,it.id,0,"",description,image,title,reviews.toInt(),"","",ratingBar.toDouble())
                var list = it.list
                var plan = CreatedPlan(it.id,it.image,it.text, list?.plus(place) )
                viewModel.updatePlan(plan)
                view?.let {
                    Snackbar.make(it,"Successfully", Snackbar.LENGTH_LONG).apply {
                        show()
                    }
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(1000)
                    findNavController().popBackStack()
                }
            }

            Log.e("data" , "title : " + title + " rating: " + ratingBar + " reviews: " + reviews)
        }
    }

    override fun onPause() {
        super.onPause()

        requireFragmentManager().beginTransaction().remove(this).commit()
    }

}