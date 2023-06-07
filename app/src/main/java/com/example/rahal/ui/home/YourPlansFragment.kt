package com.example.rahal.ui.home

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rahal.R
import com.example.rahal.databinding.FragmentYourPlansBinding
import com.example.rahal.remove.Circle
import com.example.rahal.remove.PlansAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YourPlansFragment : Fragment() {
    private lateinit var binding:FragmentYourPlansBinding
    private lateinit var createPlanButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var planList: ArrayList<Circle>
    private lateinit var adapter: PlansAdapter
    private lateinit var imageView: ImageView
    private lateinit var button: Button
    private lateinit var planName:EditText
    private lateinit var imageBackgroud:ImageView
    private lateinit var textView: TextView
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
        planList = ArrayList()


        createPlanButton = binding.createPlanButton

        adapter = PlansAdapter()

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        createPlanButton.setOnClickListener { addInfo() }



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
            val image = imageView
            val planName = planName.text.toString()
            val circle = Circle(1,R.drawable.egypt,planName)
            adapter.setCircleData(listOf(circle))
            adapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
            dialog,_->
            dialog.dismiss()
        }
        addDialog.create()
        addDialog.show()
    }

    private fun initializeVariables() {
        imageBackgroud = binding.backgroundImageView
        textView = binding.createdPlansTextView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            imageUri = data.data
            // Do something with the imageUri, such as set it to an ImageView or upload it to a server.
            imageView.setImageURI(imageUri)
        }
    }

}

