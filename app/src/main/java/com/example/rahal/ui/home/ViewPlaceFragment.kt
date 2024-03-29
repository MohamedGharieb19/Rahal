package com.example.rahal.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.rahal.R
import com.example.rahal.databinding.FragmentViewPlaceBinding
import com.example.rahal.viewModels.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern


@AndroidEntryPoint
class ViewPlaceFragment : Fragment() {
    private lateinit var binding:FragmentViewPlaceBinding
    private lateinit var options: ImageView
    private lateinit var title: TextView
    private lateinit var image: String
    private lateinit var ratingBar: RatingBar
    private lateinit var reviews: TextView
    private lateinit var description: TextView
    private lateinit var location: TextView
    lateinit var cordinates:String

    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentViewPlaceBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        intilaizeVariables()
        getData()

        options.setOnClickListener { showPopupMenu() }

        binding.mapView.setOnClickListener {
            val intentUri = "geo:0,0"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(intentUri))
            startActivity(intent) }

        binding.addToPlanButton.setOnClickListener { onAddToPlanButtonClick() }

        binding.backArrowButton.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun openMaps(){
        val pattern = Pattern.compile("\\[(.*),(.*)\\]")
        val matcher = pattern.matcher(cordinates)
        if (matcher.find()) {
            val latitude = matcher.group(1)
            val longitude = matcher.group(2)

            //val intentUri = "geo:$latitude,$longitude"
            val intentUri = "geo:0,0?q=<destination>"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(intentUri))
            startActivity(intent)
        }
    }

    private fun showPopupMenu() {
        val popupMenu = PopupMenu(requireContext(), options)
        popupMenu.menuInflater.inflate(R.menu.review_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.review -> {
                    view?.let { Navigation.findNavController(it).navigate(R.id.action_viewPlaceFragment_to_reviewFragment) }
                    // Handle menu item 1 selection
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    private fun intilaizeVariables(){
        options = binding.optionButton
        title = binding.placeNameTextView
        ratingBar = binding.starIcon
        reviews = binding.numberOfReviewsTextView
        description = binding.descriptionBodyTextView
        location = binding.location

    }

    fun getData(){
        val data = arguments
        if (data != null){
            title.text = data.getString("title").toString()
            ratingBar.rating = data.getDouble("rate").toFloat()
            reviews.text = data.getString("reviews").toString()
            description.text = data.getString("description").toString()
            location.text = data.getString("address").toString()
            image = data.getString("image").toString()
            cordinates = data.getString("location").toString()
            Log.d("testApp",cordinates)
            Glide.with(requireContext()).load(image).into(binding.imageView)
            if (description.text.equals("null") || description.text.equals("")){
                description.text = "Welcome to our place in downtown. Enjoy comfort and convenience with amenities like a fitness center, business center, and on-site restaurant. Our modern rooms feature free Wi-Fi, plush bedding, and 24/7 service. Our friendly staff is available to help make your stay unforgettable. Book now to experience the best of our city from your home away from home."
            }
        }
    }

    private fun onAddToPlanButtonClick(){
            val fragment = AddToCreatedPlansFragment()
            val bundle = Bundle()
            bundle.putString("image",image)
            bundle.putDouble("rate",ratingBar.rating.toDouble())
            bundle.putString("title",title.text.toString())
            bundle.putString("reviews",reviews.text.toString())
            bundle.putString("description",description.text.toString())
            bundle.putString("address",location.text.toString())
            //bundle.putString("location",data.location.coordiantes.toString())
            fragment.arguments = bundle
        findNavController().navigate(R.id.addToCreatedPlansFragment,bundle)

    }


}
