package com.rolandoselvera.parkinglog.view.fragments.register

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rolandoselvera.parkinglog.R
import com.rolandoselvera.parkinglog.core.base.App
import com.rolandoselvera.parkinglog.data.models.Car
import com.rolandoselvera.parkinglog.data.models.Side
import com.rolandoselvera.parkinglog.databinding.FragmentRegisterBinding
import com.rolandoselvera.parkinglog.utils.RegisterStatus
import com.rolandoselvera.parkinglog.utils.Result
import com.rolandoselvera.parkinglog.view.adapters.CarSidesAdapter
import com.rolandoselvera.parkinglog.view.fragments.base.BaseFragment
import com.rolandoselvera.parkinglog.viewmodels.register.RegisterCarViewModel
import com.rolandoselvera.parkinglog.viewmodels.register.RegisterCarViewModelFactory
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator


/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private lateinit var adapter: CarSidesAdapter
    private var sides: List<Side> = listOf()
    private val navigationArgs: RegisterFragmentArgs by navArgs()

    private var carId: Int = -1

    private val viewModel: RegisterCarViewModel by activityViewModels {
        RegisterCarViewModelFactory(
            (activity?.application as App).database.resultCarDao()
        )
    }

    override fun getViewBinding() = FragmentRegisterBinding.inflate(layoutInflater)

    override fun initializeViewModel() {
        viewModel.registerCar.observe(viewLifecycleOwner) { result ->
            when (result?.status) {
                RegisterStatus.SUCCESS -> {
                    toast(result.message)
                    goToCarsList()
                }

                RegisterStatus.ERROR -> {
                    toast(result.message)
                }

                RegisterStatus.EXCEPTION -> {
                    toast(result.message)
                }

                else -> {}
            }
        }

        viewModel.updateCar.observe(viewLifecycleOwner) { result ->
            when (result?.status) {
                RegisterStatus.SUCCESS -> {
                    toast(result.message)
                    goToCarsList()
                }

                RegisterStatus.ERROR -> {
                    toast(result.message)
                }

                RegisterStatus.EXCEPTION -> {
                    toast(result.message)
                }

                else -> {}
            }
        }

        viewModel.retrieveCar.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    val car = result.data
                    setForm(car)
                }

                is Result.Error -> {
                    toast(result.message)
                }

                else -> {}
            }
        }

        viewModel.deleteCar.observe(viewLifecycleOwner) { result ->
            when (result?.status) {
                RegisterStatus.SUCCESS -> {
                    showAlert(title = getString(R.string.success),
                        message = result.message,
                        onAccept = {
                            goToCarsList()
                        })
                }

                RegisterStatus.ERROR -> {
                    toast(result.message)
                }

                RegisterStatus.EXCEPTION -> {
                    toast(result.message)
                }

                else -> {}
            }
        }
    }

    override fun initializeViews() {
        setupRecyclerAdapter()
        setupUI()
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.registerCar.value = null
        viewModel.deleteCar.value = null
        viewModel.retrieveCar.value = null
        viewModel.updateCar.value = null
        viewModel.registerCar.removeObservers(viewLifecycleOwner)
        viewModel.deleteCar.removeObservers(viewLifecycleOwner)
        viewModel.retrieveCar.removeObservers(viewLifecycleOwner)
        viewModel.updateCar.removeObservers(viewLifecycleOwner)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                goToCarsList()
                true
            }

            R.id.delete -> {
                if (carId > -1) {
                    showAlert(
                        title = getString(R.string.delete),
                        message = getString(R.string.want_delete),
                        onAccept = {
                            viewModel.deleteCarById(carId)
                            goToCarsList()
                        }, onCancel = {})
                }
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_register, menu)
    }


    private fun setupUI() {
        carId = navigationArgs.carId
        if (carId > -1) {
            viewModel.getCarById(carId)
        }

        binding.apply {
            containerSides.recyclerView.isNestedScrollingEnabled = false

            buttonSave.setOnClickListener {
                hideKeyboard()
                if (carId > -1) updateCar() else insertCar()
            }

            buttonCancel.setOnClickListener {
                hideKeyboard()
                goToCarsList()
            }
        }
    }

    private fun setForm(car: Car?) {
        binding.apply {
            fieldBrand.setText(car?.brand)
            fieldModel.setText(car?.model)
            fieldCarPlate.setText(car?.carPlate)
            fieldColor.setText(car?.color)
            fieldOwner.setText(car?.owner)
        }
    }

    private fun setupRecyclerAdapter() {
        sides = sidesList()

        adapter = CarSidesAdapter { side ->
            sides
            goToCarDetail(side)
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

    private fun validateForm(): Boolean {
        var error = false

        binding.apply {
            fieldBrand.validator().nonEmpty().addErrorCallback {
                tilBrand.isErrorEnabled = true
                if (it.contains("empty", true)) {
                    tilBrand.error = getString(R.string.required_field)
                } else {
                    tilBrand.error = getString(R.string.required_field)
                }
                error = true
            }.addSuccessCallback {
                tilBrand.isErrorEnabled = false
                tilBrand.error = null
            }.check()

            fieldModel.validator().nonEmpty().addErrorCallback {
                tilModel.isErrorEnabled = true
                if (it.contains("empty", true)) {
                    tilModel.error = getString(R.string.required_field)
                } else {
                    tilModel.error = getString(R.string.required_field)
                }
                error = true
            }.addSuccessCallback {
                tilModel.isErrorEnabled = false
                tilModel.error = null
            }.check()

            fieldCarPlate.validator().nonEmpty().addErrorCallback {
                tilCarPlate.isErrorEnabled = true
                if (it.contains("empty", true)) {
                    tilCarPlate.error = getString(R.string.required_field)
                } else {
                    tilCarPlate.error = getString(R.string.required_field)
                }
                error = true
            }.addSuccessCallback {
                tilCarPlate.isErrorEnabled = false
                tilCarPlate.error = null
            }.check()

            fieldColor.validator().nonEmpty().addErrorCallback {
                tilColor.isErrorEnabled = true
                if (it.contains("empty", true)) {
                    tilColor.error = getString(R.string.required_field)
                } else {
                    tilColor.error = getString(R.string.required_field)
                }
                error = true
            }.addSuccessCallback {
                tilColor.isErrorEnabled = false
                tilColor.error = null
            }.check()
        }

        return error
    }

    private fun insertCar() {
        binding.apply {
            if (!validateForm()) {
                val request = Car(
                    brand = fieldBrand.text?.trim().toString(),
                    model = fieldModel.text?.trim().toString(),
                    carPlate = fieldCarPlate.text?.trim().toString(),
                    color = fieldColor.text?.trim().toString(),
                    owner = fieldOwner.text?.takeIf { it.isNotEmpty() }?.trim()?.toString()
                        ?: getString(R.string.none),
                    frontSide = "",
                    backSide = "",
                    leftSide = "",
                    rightSide = ""
                )

                viewModel.registerCar(request)
            }
        }
    }

    private fun updateCar() {
        binding.apply {
            if (!validateForm()) {
                val request = Car(
                    id = carId,
                    brand = fieldBrand.text?.trim().toString(),
                    model = fieldModel.text?.trim().toString(),
                    carPlate = fieldCarPlate.text?.trim().toString(),
                    color = fieldColor.text?.trim().toString(),
                    owner = fieldOwner.text?.takeIf { it.isNotEmpty() }?.trim()?.toString()
                        ?: getString(R.string.none),
                    frontSide = "",
                    backSide = "",
                    leftSide = "",
                    rightSide = ""
                )

                viewModel.updateCar(request)
            }
        }
    }

    private fun goToCarsList() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToCarsListFragment()
        findNavController().navigate(action)
    }

    private fun goToCarDetail(side: Side) {
        val action = RegisterFragmentDirections.actionRegisterFragmentToCarDetailFragment()
        findNavController().navigate(action)
    }
}