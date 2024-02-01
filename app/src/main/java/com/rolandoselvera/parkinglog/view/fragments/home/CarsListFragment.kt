package com.rolandoselvera.parkinglog.view.fragments.home

import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.rolandoselvera.parkinglog.data.models.Car
import com.rolandoselvera.parkinglog.databinding.FragmentCarsListBinding
import com.rolandoselvera.parkinglog.view.adapters.CarsListAdapter
import com.rolandoselvera.parkinglog.view.fragments.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 * Use the [CarsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CarsListFragment : BaseFragment<FragmentCarsListBinding>() {

    private lateinit var adapter: CarsListAdapter
    private var cars: List<Car> = listOf()

    override fun getViewBinding() = FragmentCarsListBinding.inflate(layoutInflater)

    override fun initializeViews() {
        setupRecyclerAdapter()
        setupUI()
    }

    override fun onResume() {
        super.onResume()
        val toolbar = (activity as AppCompatActivity?)?.supportActionBar
        toolbar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun setupUI() {
        binding.apply {
            if (cars.isEmpty()) containerStates.root.visibility = View.VISIBLE

            containerCarsList.root.visibility = View.VISIBLE

            fabAdd.setOnClickListener {
                goToRegister()
            }
            fabShrink(containerCarsList.recyclerView, fabAdd)
        }
    }

    private fun setupRecyclerAdapter() {
        // TODO: Add cars!!!
        cars = listOf() // carsListMock()

        adapter = CarsListAdapter(requireContext()) {
            cars
            Toast.makeText(requireContext(), it.brand, Toast.LENGTH_SHORT).show()
        }
        binding.containerCarsList.recyclerView.adapter = adapter
        adapter.submitList(cars.toList())
    }

    private fun goToRegister() {
        val action = CarsListFragmentDirections.actionCarsListFragmentToRegisterFragment()
        findNavController().navigate(action)
    }

    private fun carsListMock(): List<Car> {
        return listOf(
            Car(
                id = 1,
                brand = "Toyota",
                model = "Corolla",
                carPlate = "ABC-1234",
                color = "Azul",
                owner = "Juan Pérez"
            ),
            Car(
                id = 2,
                brand = "Honda",
                model = "Civic",
                carPlate = "XYZ-5678",
                color = "Rojo",
                owner = "Maria González"
            ),
            Car(
                id = 3,
                brand = "Ford",
                model = "Focus",
                carPlate = "DEF-9876",
                color = "Negro",
                owner = "Carlos Rodríguez"
            ),
            Car(
                id = 4,
                brand = "Chevrolet",
                model = "Malibu",
                carPlate = "GHI-6543",
                color = "Blanco",
                owner = "Ana López"
            ),
            Car(
                id = 5,
                brand = "Volkswagen",
                model = "Jetta",
                carPlate = "JKL-3210",
                color = "Plateado",
                owner = "Pedro Ramirez"
            ),
            Car(
                id = 6,
                brand = "Nissan",
                model = "Altima",
                carPlate = "MNO-7890",
                color = "Verde",
                owner = "Laura Martínez"
            ),
            Car(
                id = 7,
                brand = "Hyundai",
                model = "Elantra",
                carPlate = "PQR-4567",
                color = "Gris",
                owner = "Javier Sánchez"
            ),
            Car(
                id = 8,
                brand = "Kia",
                model = "Optima",
                carPlate = "STU-2345",
                color = "Amarillo",
                owner = "Isabel García"
            ),
            Car(
                id = 9,
                brand = "Mazda",
                model = "Mazda3",
                carPlate = "VWX-8901",
                color = "Morado",
                owner = "Ricardo Flores"
            ),
            Car(
                id = 10,
                brand = "Subaru",
                model = "Impreza",
                carPlate = "YZA-5678",
                color = "Naranja",
                owner = "Sofía Díaz"
            )
        )
    }
}