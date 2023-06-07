package com.example.rahal.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rahal.R
import com.example.rahal.data.search.City
import com.example.rahal.databinding.ViewAllActivitiesItemBinding

class SearchAdapter(): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    lateinit var onPlaceItemClick: ((City)  -> Unit )

    private val diffUtil = object : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)

    class ViewHolder(val binding: ViewAllActivitiesItemBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ViewAllActivitiesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        Glide.with(holder.itemView).load(data.image).into(holder.binding.imageCircleId)
        holder.binding.placeNameTextView.text = data.name
        holder.binding.rateTextView.text = data.rating.toString()
        holder.binding.starIcon.rating = data.rating.toFloat()
        holder.binding.locationPlace.text = data.location.address

        holder.itemView.setOnClickListener {
            onPlaceItemClick.invoke(data)
        }
    }
}