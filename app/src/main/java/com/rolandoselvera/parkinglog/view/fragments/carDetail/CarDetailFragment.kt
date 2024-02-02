package com.rolandoselvera.parkinglog.view.fragments.carDetail

import android.view.MotionEvent
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rolandoselvera.parkinglog.databinding.FragmentCarDetailBinding
import com.rolandoselvera.parkinglog.view.fragments.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 * Use the [CarDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CarDetailFragment : BaseFragment<FragmentCarDetailBinding>() {


    override fun getViewBinding() = FragmentCarDetailBinding.inflate(layoutInflater)

    override fun initializeViewModel() {
    }

    override fun initializeViews() {
        hideToolbar()
        setupUI()
    }

    private fun setupUI() {
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

    private fun setupDrawingView() {
        binding.drawingView.setOnTouchListener { v, event ->
            handleTouchEvent(event)
        }
    }

    private fun handleTouchEvent(event: MotionEvent): Boolean {
        return binding.drawingView.handleTouchEvent(event)
    }

    private fun goToRegisterCar() {
        val action = CarDetailFragmentDirections.actionCarDetailFragmentToRegisterFragment()
        findNavController().navigate(action)
    }
}