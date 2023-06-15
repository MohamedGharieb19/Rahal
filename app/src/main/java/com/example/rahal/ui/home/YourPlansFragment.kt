package com.example.rahal.ui.home

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rahal.R
import com.example.rahal.adapters.CreatedPlansAdapter
import com.example.rahal.data.createPlans.CreatedPlan
import com.example.rahal.data.createPlans.PlacesInCreatedPlan
import com.example.rahal.databinding.FragmentYourPlansBinding
import com.example.rahal.viewModels.ViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YourPlansFragment : Fragment() {
    private lateinit var binding:FragmentYourPlansBinding
    private lateinit var createPlanButton: Button
    private lateinit var createdPlansAdapter: CreatedPlansAdapter
    private lateinit var imageView: ImageView
    private lateinit var button: Button
    private lateinit var planName:EditText
    private lateinit var imageBackgroud:ImageView
    private lateinit var textView: TextView
    private val viewModel: ViewModel by viewModels()
    var imageUri:Uri? = null

    companion object {
        const val PICK_IMAGE_REQUEST = 1
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentYourPlansBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeVariables()
        setupRecyclerView()
        getCreatedPlans()
        onPlanClick()

        createPlanButton.setOnClickListener { addInfo() }

        createdPlansAdapter.setOnPlanItemLongClickListener {
            deleteCreatedPlan()
        }

    }

    private fun setupRecyclerView(){
        createdPlansAdapter = CreatedPlansAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = createdPlansAdapter
        }
    }

    private fun addInfo() {
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.add_plan_item,null)

        button = view.findViewById(R.id.button)
        imageView = view.findViewById(R.id.icon_upload)
        planName = view.findViewById(R.id.plan_name_edit_text)

        button.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        val addDialog = AlertDialog.Builder(context)
        addDialog.setView(view)
        addDialog.setPositiveButton("Ok"){
            dialog,_->
            val planName = planName.text.toString()
            val plan = CreatedPlan(0,imageUri.toString() ,planName, mutableListOf())


            viewModel.insertPlan(plan)
            getCreatedPlans()

            createdPlansAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
            dialog,_->
            dialog.dismiss()
        }
        addDialog.create()
        addDialog.show()
    }

    private fun getCreatedPlans(){
        viewModel.getCreatedPlans().observe(viewLifecycleOwner, Observer { data ->
            if (data.isEmpty()){
                createdPlansAdapter.differ.submitList(null)
                showNoPlans()
            }else {
                createdPlansAdapter.differ.submitList(data)
                hideNoPlans()
            }
        })
    }

    private fun initializeVariables() {
        imageBackgroud = binding.backgroundImageView
        textView = binding.createdPlansTextView
        createPlanButton = binding.createPlanButton

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            imageUri = data.data
            // Do something with the imageUri, such as set it to an ImageView or upload it to a server.
            imageView.setImageURI(imageUri)
        }
    }

    private fun onPlanClick(){
        createdPlansAdapter.onPlanItemClick = { data ->
            val fragment = ViewPlanFragment()
            val bundle = Bundle()
            //bundle.putParcelable("list",data.list.component1())
            bundle.putParcelableArrayList("list", ArrayList(data!!.list))
            Log.e("list",data.list.toString())
            fragment.arguments = bundle
            findNavController().navigate(R.id.action_plansFragment_to_viewYourCreatedPlanFragment,bundle)
        }
    }

    private fun showNoPlans(){
        binding.backgroundImageView.visibility = View.VISIBLE
        binding.createdPlansTextView.visibility = View.VISIBLE
        binding.createYourPlanTextView.visibility = View.VISIBLE
    }
    private fun hideNoPlans(){
        binding.backgroundImageView.visibility = View.INVISIBLE
        binding.createdPlansTextView.visibility = View.INVISIBLE
        binding.createYourPlanTextView.visibility = View.INVISIBLE
    }

//    private fun deleteCreatedPlan(){
//        createdPlansAdapter.onPlanItemLongClick = {data ->
//            viewModel.deletePlan(data)
//            view?.let {
//                Snackbar.make(it,"Successfully delete plan", Snackbar.LENGTH_LONG).apply {
//                    setAction("Undo"){
//                        viewModel.insertPlan(data)
//                    }
//                    show()
//                }
//            }
//        }
//    }

    private fun deleteCreatedPlan() {
        createdPlansAdapter.onPlanItemLongClick = { data ->
            val alertDialog = AlertDialog.Builder(requireContext())
                .setTitle("Delete Plan")
                .setMessage("Are you sure you want to delete this plan?")
                .setPositiveButton("Delete") { _, _ ->
                    viewModel.deletePlan(data)
                    view?.let {
                        Snackbar.make(it, "Successfully deleted plan", Snackbar.LENGTH_LONG).apply {
                            setAction("Undo") {
                                viewModel.insertPlan(data)
                            }
                            show()
                        }
                    }
                }
                .setNegativeButton("Cancel", null)
                .create()

            alertDialog.show()
        }
    }


}



