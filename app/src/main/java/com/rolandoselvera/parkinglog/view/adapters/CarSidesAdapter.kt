package com.rolandoselvera.parkinglog.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rolandoselvera.parkinglog.data.models.Side
import com.rolandoselvera.parkinglog.databinding.ItemSideBinding

class CarSidesAdapter(private val onItemClicked: (Side) -> Unit) :
    ListAdapter<Side, CarSidesAdapter.CarSideViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarSideViewHolder {
        return CarSideViewHolder(
            ItemSideBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CarSideViewHolder, position: Int) {
        val current = getItem(position)

        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }

        holder.bind(current)
    }

    inner class CarSideViewHolder(private var binding: ItemSideBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(side: Side) {
            binding.apply {
                txtSide.text = side.sideCar
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Side>() {
            override fun areItemsTheSame(oldItem: Side, newItem: Side): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Side, newItem: Side): Boolean {
                return oldItem.sideCar == newItem.sideCar
            }

        }
    }
}