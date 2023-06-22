package com.example.rahal.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rahal.R
import com.example.rahal.data.Place
import com.example.rahal.databinding.CustomRectangleItemForRecylcerViewHomePageBinding

class RecommendedTopRatedAdapter(): RecyclerView.Adapter<RecommendedTopRatedAdapter.viewHolder>() {
    lateinit var onPlaceItemClick: ((Place)  -> Unit )

    lateinit var onFavoritesIconClick: ((Place)  -> Unit )

    class viewHolder(val binding: CustomRectangleItemForRecylcerViewHomePageBinding)
        : RecyclerView.ViewHolder(binding.root) {

    }

    private val diffUtil = object : DiffUtil.ItemCallback<Place>(){
        override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem._id== newItem._id
        }

        override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(CustomRectangleItemForRecylcerViewHomePageBinding
            .inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }



    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val data = differ.currentList[position]
        Glide.with(holder.itemView).load(data.image).into(holder.binding.imageViewId)
        holder.binding.rateTextView.text = data.rating.toString()
        holder.binding.placeTextView.text = data.name
        holder.binding.locationPlace.text = data.location.address

        if (data.image.isNullOrEmpty()){
            holder.binding.imageViewId.setImageResource(R.drawable.museums)
        }

        holder.itemView.setOnClickListener {
            onPlaceItemClick.invoke(data)
        }

        holder.binding.favoriteIcon.setOnClickListener {
            holder.binding.favoriteIcon.setImageResource(R.drawable.ic_favorite)
            onFavoritesIconClick.invoke(data)
        }
    }
}