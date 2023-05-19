package com.example.rahal.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rahal.R
import com.example.rahal.adapters.FavoritesAdapter
import com.example.rahal.databinding.FragmentFavoritesBinding
import com.example.rahal.viewModels.ViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel : ViewModel by viewModels()
    private lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFavorites()
        deleteFavorite()
        onPlaceClick()

    }

    private fun getFavorites(){
        setupRecyclerView()
        viewModel.getFavorites().observe(viewLifecycleOwner, Observer { data ->
            if (data.isEmpty()){
                showNoFavorites()
                favoritesAdapter.differ.submitList(null)
            }else {
                hideNoFavorites()
                favoritesAdapter.differ.submitList(data)
            }
        })
    }

    private fun deleteFavorite(){
        favoritesAdapter.onFavoritesIconClick = {data ->
            viewModel.delete(data)
            view?.let {
                Snackbar.make(it,"Successfully deleted article",Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.upsert(data)
                    }
                    show()
                }
            }
        }
    }

    private fun setupRecyclerView(){
        favoritesAdapter = FavoritesAdapter()
        binding.favoritesRecyclerView.apply {
            layoutManager = GridLayoutManager(context,2, RecyclerView.VERTICAL,false)
            adapter = favoritesAdapter
        }
    }

    private fun showNoFavorites(){
        binding.backgroundImageView.visibility = View.VISIBLE
        binding.resultsTextView.visibility = View.VISIBLE
    }
    private fun hideNoFavorites(){
        binding.backgroundImageView.visibility = View.INVISIBLE
        binding.resultsTextView.visibility = View.INVISIBLE
    }

    private fun onPlaceClick(){
        favoritesAdapter.onPlaceItemClick = { data ->
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
            findNavController().navigate(R.id.action_favoritesFragment_to_viewPlaceFragment,bundle)
        }
    }

}