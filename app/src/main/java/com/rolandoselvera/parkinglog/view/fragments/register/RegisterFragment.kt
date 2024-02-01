package com.rolandoselvera.parkinglog.view.fragments.register

import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rolandoselvera.parkinglog.R
import com.rolandoselvera.parkinglog.data.models.Side
import com.rolandoselvera.parkinglog.databinding.FragmentRegisterBinding
import com.rolandoselvera.parkinglog.view.adapters.CarSidesAdapter
import com.rolandoselvera.parkinglog.view.fragments.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private lateinit var adapter: CarSidesAdapter
    private var sides: List<Side> = listOf()
    override fun getViewBinding() = FragmentRegisterBinding.inflate(layoutInflater)
    override fun initializeViews() {
        setupRecyclerAdapter()
        setupUI()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                goToCarsList()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun setupUI() {
        binding.apply {
            containerSides.recyclerView.isNestedScrollingEnabled = false

            setHasOptionsMenu(true)
            val toolbar = (activity as AppCompatActivity?)?.supportActionBar
            toolbar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupRecyclerAdapter() {
        sides = sidesList()

        adapter = CarSidesAdapter {
            sides
            Toast.makeText(requireContext(), it.sideCar, Toast.LENGTH_SHORT).show()
        }
        binding.containerSides.recyclerView.adapter = adapter
        adapter.submitList(sides.toList())
    }

    private fun sidesList(): List<Side> {
        return listOf(
            Side(getString(R.string.front)),
            Side(getString(R.string.back)),
            Side(getString(R.string.right_side)),
            Side(getString(R.string.left_side))
        )
    }

    private fun goToCarsList() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToCarsListFragment()
        findNavController().navigate(action)
    }
}