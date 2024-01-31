package com.rolandoselvera.parkinglog.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.rolandoselvera.parkinglog.databinding.FragmentCarsListBinding

/**
 * A simple [Fragment] subclass.
 * Use the [CarsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CarsListFragment : BaseFragment<FragmentCarsListBinding>() {
    override fun getViewBinding() = FragmentCarsListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            //txtExample.text = "This is from Fragment!"
        }
    }
}