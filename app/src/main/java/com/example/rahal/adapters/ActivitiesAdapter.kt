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

    class viewHolder(val binding: CustomCircularItemForRecyclerViewBinding):RecyclerView.ViewHolder(binding.root) {

    }

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
        holder.binding.imageCircleId.setImageResource(R.drawable.kfc)

        holder.itemView.setOnClickListener {
            onActivityItemClick.invoke(data)
        }

    }


}