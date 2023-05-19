package com.example.rahal.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rahal.R
import com.example.rahal.adapters.FaqAdapter
import com.example.rahal.data.faq.FaqItem
import com.example.rahal.databinding.FragmentFaqBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FAQFragment : Fragment() {
    private lateinit var binding: FragmentFaqBinding
    private lateinit var recyclerview: RecyclerView
    private val data = ArrayList<FaqItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFaqBinding.inflate(inflater,container,false)
        intilaizeVariables()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        activity?.run {
            supportFragmentManager.beginTransaction().remove(FAQFragment())
                .commitAllowingStateLoss()
        }

        recyclerView()



    }

    private fun intilaizeVariables() {
        recyclerview = binding.recylcerView

    }

    private fun recyclerView(){
        recyclerview.layoutManager = LinearLayoutManager(context)

        data.add(FaqItem("Trip Plan", "Which plan is right for me? Our plans – Basix, Original, Comfort and Premium – cover a whole gamut of travel experiences. To find out which one has ‘You’ written all over it, visit our Are trips physically demanding? Want to lie in a hammock and not move until cocktail hour? We’ve got a trip for that. Want to power up the side of a mountain at high altitude? We’ve also got a trip for that. To determine what type of trip suits you best, each of our trips comes with a Physical Rating to let you know how physically demanding it is… or isn’t."))
        data.add(FaqItem("Trip Plan", "Which plan is right for me? Our plans – Basix, Original, Comfort and Premium – cover a whole gamut of travel experiences. To find out which one has ‘You’ written all over it, visit our Are trips physically demanding? Want to lie in a hammock and not move until cocktail hour? We’ve got a trip for that. Want to power up the side of a mountain at high altitude? We’ve also got a trip for that. To determine what type of trip suits you best, each of our trips comes with a Physical Rating to let you know how physically demanding it is… or isn’t."))
        data.add(FaqItem("Trip Plan", "Which plan is right for me? Our plans – Basix, Original, Comfort and Premium – cover a whole gamut of travel experiences. To find out which one has ‘You’ written all over it, visit our Are trips physically demanding? Want to lie in a hammock and not move until cocktail hour? We’ve got a trip for that. Want to power up the side of a mountain at high altitude? We’ve also got a trip for that. To determine what type of trip suits you best, each of our trips comes with a Physical Rating to let you know how physically demanding it is… or isn’t."))
        data.add(FaqItem("Trip Plan", "Which plan is right for me? Our plans – Basix, Original, Comfort and Premium – cover a whole gamut of travel experiences. To find out which one has ‘You’ written all over it, visit our Are trips physically demanding? Want to lie in a hammock and not move until cocktail hour? We’ve got a trip for that. Want to power up the side of a mountain at high altitude? We’ve also got a trip for that. To determine what type of trip suits you best, each of our trips comes with a Physical Rating to let you know how physically demanding it is… or isn’t."))
        data.add(FaqItem("Trip Plan", "Which plan is right for me? Our plans – Basix, Original, Comfort and Premium – cover a whole gamut of travel experiences. To find out which one has ‘You’ written all over it, visit our Are trips physically demanding? Want to lie in a hammock and not move until cocktail hour? We’ve got a trip for that. Want to power up the side of a mountain at high altitude? We’ve also got a trip for that. To determine what type of trip suits you best, each of our trips comes with a Physical Rating to let you know how physically demanding it is… or isn’t."))
        data.add(FaqItem("Trip Plan", "Which plan is right for me? Our plans – Basix, Original, Comfort and Premium – cover a whole gamut of travel experiences. To find out which one has ‘You’ written all over it, visit our Are trips physically demanding? Want to lie in a hammock and not move until cocktail hour? We’ve got a trip for that. Want to power up the side of a mountain at high altitude? We’ve also got a trip for that. To determine what type of trip suits you best, each of our trips comes with a Physical Rating to let you know how physically demanding it is… or isn’t."))
        data.add(FaqItem("Trip Plan", "Which plan is right for me? Our plans – Basix, Original, Comfort and Premium – cover a whole gamut of travel experiences. To find out which one has ‘You’ written all over it, visit our Are trips physically demanding? Want to lie in a hammock and not move until cocktail hour? We’ve got a trip for that. Want to power up the side of a mountain at high altitude? We’ve also got a trip for that. To determine what type of trip suits you best, each of our trips comes with a Physical Rating to let you know how physically demanding it is… or isn’t."))

        // This will pass the ArrayList to our Adapter
        val adapter = FaqAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }




}