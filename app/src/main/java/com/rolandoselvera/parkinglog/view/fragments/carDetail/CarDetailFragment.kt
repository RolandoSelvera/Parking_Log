package com.rolandoselvera.parkinglog.view.fragments.carDetail

import android.view.MotionEvent
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.rolandoselvera.parkinglog.R
import com.rolandoselvera.parkinglog.databinding.FragmentCarDetailBinding
import com.rolandoselvera.parkinglog.view.fragments.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 * Use the [CarDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CarDetailFragment : BaseFragment<FragmentCarDetailBinding>() {

    private val navigationArgs: CarDetailFragmentArgs by navArgs()
    private var side: String = ""
    private var carId: Int = -1

    override fun getViewBinding() = FragmentCarDetailBinding.inflate(layoutInflater)

    override fun initializeViewModel() {
    }

    override fun initializeViews() {
        hideToolbar()
        setupUI()
    }

    private fun setupUI() {
        side = navigationArgs.side
        carId = navigationArgs.carId

        setupCarSide(side)

        setupDrawingView()

        binding.apply {
            fabBack.setOnClickListener {
                goToRegisterCar()
            }

            fabDelete.setOnClickListener {
                drawingView.clearDrawing()
            }

            fabSave.setOnClickListener {
                // TODO: Take screenshot and save!
                toast("TODO: Screenshot save!")
                goToRegisterCar()
            }
        }
    }

    private fun setupCarSide(side: String) {
        binding.apply {
            val imageResource = when (side.lowercase()) {
                getString(R.string.front).lowercase() -> R.drawable.img_car_front
                getString(R.string.back).lowercase() -> R.drawable.img_car_back
                getString(R.string.left_side).lowercase() -> R.drawable.img_car_left
                getString(R.string.right_side).lowercase() -> R.drawable.img_car_right
                else -> R.drawable.ic_img_preview
            }

            imgCar.load(imageResource) {
                placeholder(R.drawable.ic_img_preview)
                crossfade(true)
            }
        }
    }

    private fun setupDrawingView() {
        binding.drawingView.setOnTouchListener { v, event ->
            handleTouchEvent(event)
        }
    }

    private fun handleTouchEvent(event: MotionEvent): Boolean {
        return binding.drawingView.handleTouchEvent(event)
    }

    private fun goToRegisterCar() {
        val action = CarDetailFragmentDirections.actionCarDetailFragmentToRegisterFragment(carId)
        findNavController().navigate(action)
    }
}