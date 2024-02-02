package com.rolandoselvera.parkinglog.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rolandoselvera.parkinglog.R
import com.rolandoselvera.parkinglog.data.models.Car
import com.rolandoselvera.parkinglog.databinding.ItemCarBinding

class CarsListAdapter(private val context: Context, private val onItemClicked: (Car) -> Unit) :
    ListAdapter<Car, CarsListAdapter.CarViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        return CarViewHolder(
            ItemCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val current = getItem(position)

        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }

        holder.bind(current)
    }

    inner class CarViewHolder(private var binding: ItemCarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(car: Car) {
            binding.apply {
                txtTitle.text =
                    context.getString(R.string.car_title, car.brand, car.model, car.carPlate)
                txtColor.text = car.color
                txtOwner.text = car.owner
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Car>() {
            override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}