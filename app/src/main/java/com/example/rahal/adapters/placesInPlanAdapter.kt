package com.example.rahal.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rahal.data.suggestedPlans.PlaceInPlan
import com.example.rahal.data.suggestedPlans.Plan
import com.example.rahal.databinding.CustomRectangleItemForPlansBinding
import com.example.rahal.databinding.PlanItemBinding

class placesInPlanAdapter(): RecyclerView.Adapter<placesInPlanAdapter.viewHolder>() {

    lateinit var onPlaceInPlanClick: ((PlaceInPlan)  -> Unit )

    private val diffUtil = object : DiffUtil.ItemCallback<PlaceInPlan>(){
        override fun areItemsTheSame(oldItem: PlaceInPlan, newItem: PlaceInPlan): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: PlaceInPlan, newItem: PlaceInPlan): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)

    class viewHolder(val binding: PlanItemBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(PlanItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val data = differ.currentList[position]
        Glide.with(holder.itemView).load(data.image).into(holder.binding.placeImage)
        holder.binding.locationPlace.text = data.location.address
        holder.binding.rateTextView.text = data.rating.toString()
        holder.binding.placeNameTextView.text = data.name
        holder.binding.starIcon.rating = data.rating.toFloat()


        holder.itemView.setOnClickListener {
            onPlaceInPlanClick.invoke(data)
        }

    }


}