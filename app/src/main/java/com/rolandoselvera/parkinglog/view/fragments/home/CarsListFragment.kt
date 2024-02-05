package com.rolandoselvera.parkinglog.view.fragments.home

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.rolandoselvera.parkinglog.core.base.App
import com.rolandoselvera.parkinglog.data.local.preferences.PreferencesProvider
import com.rolandoselvera.parkinglog.data.models.Car
import com.rolandoselvera.parkinglog.databinding.FragmentCarsListBinding
import com.rolandoselvera.parkinglog.view.adapters.CarsListAdapter
import com.rolandoselvera.parkinglog.view.fragments.base.BaseFragment
import com.rolandoselvera.parkinglog.viewmodels.home.CarListViewModel
import com.rolandoselvera.parkinglog.viewmodels.home.CarsViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [CarsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CarsListFragment : BaseFragment<FragmentCarsListBinding>() {

    private lateinit var adapter: CarsListAdapter
    private var carsList: List<Car> = listOf()

    private val viewModel: CarListViewModel by activityViewModels {
        CarsViewModelFactory(
            (activity?.application as App).database.resultCarDao()
        )
    }

    override fun getViewBinding() = FragmentCarsListBinding.inflate(layoutInflater)

    override fun initializeViewModel() {
        viewModel.allCarsDb.observe(this.viewLifecycleOwner) { cars ->
            cars.let {
                adapter.submitList(cars)
                carsList = cars
                setupUI()
            }
        }
    }

    override fun initializeViews() {
        setupRecyclerAdapter()
    }

    override fun onResume() {
        super.onResume()
        val toolbar = (activity as AppCompatActivity?)?.supportActionBar
        toolbar?.setDisplayHomeAsUpEnabled(false)
        PreferencesProvider.removePreferences()
    }

    private fun setupUI() {
        binding.apply {
            containerStates.root.visibility = if (carsList.isEmpty()) View.VISIBLE else View.GONE

            containerCarsList.root.visibility = View.VISIBLE

            fabAdd.setOnClickListener {
                goToRegister()
            }
            fabShrink(containerCarsList.recyclerView, fabAdd)
        }
    }

    private fun setupRecyclerAdapter() {
        adapter = CarsListAdapter(requireContext()) { car ->
            carsList
            goToRegister(car.id)
        }
        binding.containerCarsList.recyclerView.adapter = adapter
        adapter.submitList(carsList.toList())
    }

    private fun goToRegister(carId: Int = -1) {
        val action = CarsListFragmentDirections.actionCarsListFragmentToRegisterFragment(carId)
        findNavController().navigate(action)
    }
}