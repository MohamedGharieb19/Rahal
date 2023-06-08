package com.example.rahal.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.rahal.R
import com.example.rahal.adapters.RecommendedAdapter
import com.example.rahal.databinding.FragmentRecommendedPlansBinding
import com.example.rahal.viewModels.ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendedPlansFragment : Fragment() {
    private lateinit var binding: FragmentRecommendedPlansBinding
    private lateinit var recommendedAdapter: RecommendedAdapter
    private val viewModel : ViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRecommendedPlansBinding.inflate(inflater,container,false)
        getRecommendedPlans()
        onPlanClick()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }

    private fun getRecommendedPlans(){
        setupRecyclerView()
        viewModel.getRecommendedPlans()
        viewModel.getRecommendedPlansLiveData.observe(viewLifecycleOwner, Observer {
            recommendedAdapter.differ.submitList(it)
        })

    }

    private fun setupRecyclerView(){
        recommendedAdapter = RecommendedAdapter()
        binding.recommendedPlansRecyclerView.apply {
            adapter = recommendedAdapter
        }

    }

    private fun onPlanClick(){
        recommendedAdapter.onPlanClick = { data ->
            val fragment = ViewPlanFragment()
            val bundle = Bundle()

            bundle.putParcelableArrayList("list",ArrayList(data!!.places))



            fragment.arguments = bundle
            findNavController().navigate(R.id.action_plansFragment_to_planFragment,bundle)

        }
    }




}