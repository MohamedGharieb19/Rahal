package com.example.rahal.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rahal.R
import com.example.rahal.databinding.CustomCircularItemForRecyclerViewBinding

class ActivitiesAdapter():RecyclerView.Adapter<ActivitiesAdapter.viewHolder>() {

    lateinit var onActivityItemClick: ((String) -> String)
    class viewHolder(val binding: CustomCircularItemForRecyclerViewBinding):RecyclerView.ViewHolder(binding.root) {}

    val diffUtil = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.length == newItem.length
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(CustomCircularItemForRecyclerViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.binding.circleTextView.text = data

        when (holder.binding.circleTextView.text) {
            "Boat Tours & Water Sports" -> holder.binding.imageCircleId.setImageResource(R.drawable.watersports)
            "Classes & Workshops" -> holder.binding.imageCircleId.setImageResource(R.drawable.classes)
            "Concerts & Shows" -> holder.binding.imageCircleId.setImageResource(R.drawable.concerts)
            "Food & Drink" -> holder.binding.imageCircleId.setImageResource(R.drawable.restaurant)
            "Fun & Games" -> holder.binding.imageCircleId.setImageResource(R.drawable.parks)
            "Museums" -> holder.binding.imageCircleId.setImageResource(R.drawable.museums)
            "Nature & Parks" -> holder.binding.imageCircleId.setImageResource(R.drawable.waterparks)
            "Nightlife" -> holder.binding.imageCircleId.setImageResource(R.drawable.nightlife)
            "Other" -> holder.binding.imageCircleId.setImageResource(R.drawable.other)
            "Outdoor Activities" -> holder.binding.imageCircleId.setImageResource(R.drawable.outdoor)
            "Shopping" -> holder.binding.imageCircleId.setImageResource(R.drawable.shopping)
            "Sights & Landmarks" -> holder.binding.imageCircleId.setImageResource(R.drawable.landmarks)
            "Spas & Wellness" -> holder.binding.imageCircleId.setImageResource(R.drawable.spas)
            "Traveler Resources" -> holder.binding.imageCircleId.setImageResource(R.drawable.travelerresources)
            "Water & Amusement Parks" -> holder.binding.imageCircleId.setImageResource(R.drawable.waterparks)
            "Zoos & Aquariums" -> holder.binding.imageCircleId.setImageResource(R.drawable.zoos)
            else -> holder.binding.imageCircleId.setImageResource(R.drawable.other)
        }

        holder.itemView.setOnClickListener {
            onActivityItemClick.invoke(data)
        }

    }
}