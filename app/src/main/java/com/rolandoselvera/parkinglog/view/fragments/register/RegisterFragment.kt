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
import com.rolandoselvera.parkinglog.data.local.preferences.PreferencesProvider
import com.rolandoselvera.parkinglog.data.models.Car
import com.rolandoselvera.parkinglog.data.models.Side
import com.rolandoselvera.parkinglog.databinding.FragmentRegisterBinding
import com.rolandoselvera.parkinglog.data.models.enums.RegisterStatus
import com.rolandoselvera.parkinglog.data.models.results.Result
import com.rolandoselvera.parkinglog.utils.toCarEntity
import com.rolandoselvera.parkinglog.utils.validateAndSetError
import com.rolandoselvera.parkinglog.view.adapters.CarSidesAdapter
import com.rolandoselvera.parkinglog.view.fragments.base.BaseFragment
import com.rolandoselvera.parkinglog.viewmodels.register.RegisterCarViewModel
import com.rolandoselvera.parkinglog.viewmodels.register.RegisterCarViewModelFactory

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

                RegisterStatus.ERROR, RegisterStatus.EXCEPTION -> {
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

                RegisterStatus.ERROR, RegisterStatus.EXCEPTION -> {
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

                RegisterStatus.ERROR, RegisterStatus.EXCEPTION -> {
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
        if (carId > -1 && PreferencesProvider.CAR_ENTITY?.id == null) {
            viewModel.getCarById(carId)
        } else {
            setTempForm()
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
            PreferencesProvider.CAR_ENTITY = car.toCarEntity()
        }
    }

    private fun setTempForm() {
        binding.apply {
            val car = PreferencesProvider.CAR_ENTITY
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
        var error: Boolean

        binding.apply {
            error = fieldBrand.validateAndSetError(tilBrand)
            error = fieldModel.validateAndSetError(tilModel)
            error = fieldCarPlate.validateAndSetError(tilCarPlate)
            error = fieldColor.validateAndSetError(tilColor)
        }

        return error
    }

    private fun insertCar() {
        binding.apply {
            if (!validateForm()) {
                val request = getNewCar()
                viewModel.registerCar(request)
            }
        }
    }

    private fun getNewCar(): Car {
        binding.apply {
            return Car(
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
        }
    }

    private fun updateCar() {
        binding.apply {
            if (!validateForm()) {
                val request = getNewInfoCar()
                viewModel.updateCar(request)
            }
        }
    }

    private fun getNewInfoCar(): Car {
        binding.apply {
            return Car(
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
        }
    }

    private fun goToCarsList() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToCarsListFragment()
        findNavController().navigate(action)
    }

    private fun goToCarDetail(side: Side) {
        setForm(getNewInfoCar())
        val action =
            RegisterFragmentDirections.actionRegisterFragmentToCarDetailFragment(
                side.sideCar,
                carId
            )
        findNavController().navigate(action)
    }
}