package com.example.rahal.ui.home

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.load.engine.Resource
import com.example.rahal.R
import com.example.rahal.adapters.SearchAdapter
import com.example.rahal.databinding.FragmentSearchBinding
import com.example.rahal.viewModels.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchEditText: EditText
    private val viewModel: ViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var resultImageView: ImageView
    private lateinit var resultTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        intilaizeVariables()
        setEditTextOnFocusMode()
        setupRecyclerView()
        getPlacesFromSearch()
        onPlaceClick()

        binding.backArrowButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    private fun setEditTextOnFocusMode(){
        searchEditText.requestFocus()
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT)
    }
    private fun intilaizeVariables(){
        searchEditText = binding.searchBarEditText
        progressBar = binding.ProgressBar
        resultImageView = binding.backgroundImageView
        resultTextView = binding.resultsTextView

    }
    private fun setupRecyclerView(){
        searchAdapter = SearchAdapter()
        binding.searchRecyclerView.apply {
            adapter = searchAdapter
        }
    }
    private fun getPlacesFromSearch(){
        var job: Job? = null

        searchEditText.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                showProgressBar()

                editable.let {
                    if (editable.toString().isNotEmpty()){
                        delay(1000)
                        viewModel.getSearch(editable.toString())
                        hideProgressBar()

                    }else if (editable.toString().isEmpty()){
                        hideProgressBar()
                        searchAdapter.differ.submitList(null)
                        hideNoResults()
                    }

                }
            }
        }

        viewModel.getSearchLiveData.observe(viewLifecycleOwner, Observer { data ->
            if (data.isEmpty()){
                showNoResults()
                searchAdapter.differ.submitList(null)
            }else if (data.isNotEmpty()) {
                hideNoResults()
                searchAdapter.differ.submitList(data)
            }
        })

    }
    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }
    private fun showNoResults(){
        resultImageView.visibility = View.VISIBLE
        resultTextView.visibility = View.VISIBLE
    }
    private fun hideNoResults(){
        resultImageView.visibility = View.INVISIBLE
        resultTextView.visibility = View.INVISIBLE
    }

    private fun onPlaceClick(){
        searchAdapter.onPlaceItemClick = { data ->
            val fragment = ViewPlaceFragment()
            val bundle = Bundle()
            bundle.putString("image",data.image)
            bundle.putDouble("rate",data.rating)
            bundle.putString("title",data.name)
            bundle.putString("reviews",data.numberOfReviews.toString())
            bundle.putString("description",data.description)
            bundle.putString("address",data.location.address)
            //bundle.putString("location",data.location.coordiantes.toString())
            fragment.arguments = bundle
            findNavController().navigate(R.id.action_searchFragment_to_viewPlaceFragment,bundle)
        }
    }



}