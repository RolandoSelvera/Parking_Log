package com.rolandoselvera.parkinglog.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.rolandoselvera.parkinglog.data.models.Car
import com.rolandoselvera.parkinglog.databinding.FragmentCarsListBinding
import com.rolandoselvera.parkinglog.view.adapter.CarsListAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [CarsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CarsListFragment : BaseFragment<FragmentCarsListBinding>() {

    private lateinit var adapter: CarsListAdapter
    private var cars: Array<Car> = arrayOf()

    override fun getViewBinding() = FragmentCarsListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerAdapter()
        setupUI()
    }

    private fun setupUI() {
        if (cars.isEmpty()) binding.containerStates.root.visibility = View.VISIBLE

        binding.containerCarsList.root.visibility = View.VISIBLE

        binding.fabAdd.setOnClickListener {
            Toast.makeText(requireContext(), "Click!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerAdapter() {
        // TODO: Add cars!!!
        cars = arrayOf()
        adapter = CarsListAdapter(requireContext()) {
            arrayOf(
                cars
            )
            Toast.makeText(requireContext(), it.brand, Toast.LENGTH_SHORT).show()
        }
        binding.containerCarsList.recyclerView.adapter = adapter
        adapter.submitList(cars.toList())
    }
}