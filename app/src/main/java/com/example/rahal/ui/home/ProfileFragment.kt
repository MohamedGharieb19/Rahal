package com.example.rahal.ui.home

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.rahal.R
import com.example.rahal.databinding.FragmentProfileBinding
import com.example.rahal.ui.user.LogInUserFragment
import com.example.rahal.ui.user.MainActivity
import com.example.rahal.viewModels.ViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var settingButton:ImageButton
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var closeButton:ImageButton
    private lateinit var profileImageView: ImageView
    private val viewModel: ViewModel by viewModels()
    companion object {
        const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            intilaizeVariable()
            getToken()


         settingButton.setOnClickListener {
             drawerLayout.openDrawer(navigationView)
         }

         closeButton.setOnClickListener {
             drawerLayout.closeDrawer(navigationView)
         }

         profileImageView.setOnClickListener {
             val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
             startActivityForResult(intent, PICK_IMAGE_REQUEST)
         }

    }

    private fun intilaizeVariable(){
        navigationView = binding.navigationView
        settingButton = binding.settingsButton
        drawerLayout = binding.drawerLayout
        closeButton = binding.closeNavigationDrawer
        profileImageView = binding.profileCircularImage

        navigationView.bringToFront()
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout -> {
                val intent = Intent(activity,MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            val imageUri = data.data
            // Do something with the imageUri, such as set it to an ImageView or upload it to a server.
            profileImageView.setImageURI(imageUri)
        }
    }

    private fun getToken(){
        val getToken = viewModel.getToken().observe(viewLifecycleOwner, Observer {
            Log.e("get token" , "get token ${it.token}")
            getProfile(it.token)
        })
    }

    private fun getProfile(token: String) {
        viewModel.getProfile(token)
        viewModel.getProfile.observe(viewLifecycleOwner, Observer {

            binding.emailEditText.hint = it.email
            binding.fullNameEditText.hint = it.name
            Log.e("user info","email: ${it.email} , name: ${it.name}")
        })

    }


}


