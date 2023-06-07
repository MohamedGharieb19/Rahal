package com.example.rahal.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rahal.data.suggestedPlans.Plan
import com.example.rahal.databinding.CustomRectangleItemForPlansBinding


class RecommendedAdapter(): RecyclerView.Adapter<RecommendedAdapter.viewHolder>() {

    lateinit var onPlanClick: ((Plan)  -> Unit )

    private val diffUtil = object : DiffUtil.ItemCallback<Plan>(){
        override fun areItemsTheSame(oldItem: Plan, newItem: Plan): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Plan, newItem: Plan): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)

    class viewHolder(val binding: CustomRectangleItemForPlansBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(CustomRectangleItemForPlansBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val data = differ.currentList[position]
        Glide.with(holder.itemView).load(data.image).into(holder.binding.imageView)
        holder.binding.textView.text = data.name

        holder.itemView.setOnClickListener {
            onPlanClick.invoke(data)
        }
    }
}