package com.example.rahal.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rahal.data.createPlans.CreatedPlan
import com.example.rahal.databinding.CustomRectangleItemForPlansBinding


class CreatedPlansAdapter(): RecyclerView.Adapter<CreatedPlansAdapter.viewHolder>() {



    lateinit var onPlanItemClick: ((CreatedPlan)  -> Unit )

    private val diffUtil = object : DiffUtil.ItemCallback<CreatedPlan>(){
        override fun areItemsTheSame(oldItem: CreatedPlan, newItem: CreatedPlan): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CreatedPlan, newItem: CreatedPlan): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)

    class viewHolder (var binding: CustomRectangleItemForPlansBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(CustomRectangleItemForPlansBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.binding.textView.text = data.text

        holder.itemView.setOnClickListener {
            onPlanItemClick.invoke(data)
        }
    }
}