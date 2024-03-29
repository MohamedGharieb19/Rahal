package com.example.rahal.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rahal.R
import com.example.rahal.data.Place
import com.example.rahal.databinding.CustomRectangleItemForRecylerViewBinding

class FavoritesAdapter(): RecyclerView.Adapter<FavoritesAdapter.viewHolder>()  {

    lateinit var onFavoritesIconClick: ((Place)  -> Unit )

    lateinit var onPlaceItemClick: ((Place)  -> Unit )

    private val diffUtil = object : DiffUtil.ItemCallback<Place>(){
        override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)

    class viewHolder(val binding: CustomRectangleItemForRecylerViewBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(CustomRectangleItemForRecylerViewBinding
            .inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.binding.rateTextView.text = data.rating.toString()
        holder.binding.placeTextView.text = data.name
        holder.binding.locationPlace.text = data.location.address

        if(data.image.isNullOrEmpty()){
            data.image = "https://media-cdn.tripadvisor.com/media/photo-o/09/60/28/be/nino-s-italian-restaurant.jpg"

        }

        holder.binding.favoriteIcon.setOnClickListener {
            holder.binding.favoriteIcon.setImageResource(R.drawable.ic_favorite)
            onFavoritesIconClick.invoke(data)
        }

        holder.itemView.setOnClickListener {
            onPlaceItemClick.invoke(data)
        }

    }
}