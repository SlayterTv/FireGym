package com.slaytertv.firegym.ui.view.sliderini

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.slaytertv.firegym.R
import com.slaytertv.firegym.databinding.FragmentSlider1Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Slider1Fragment : Fragment() {
    val TAG:String="Slider1Fragment"
    lateinit var binding: FragmentSlider1Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSlider1Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        botoneNB()
    }

    private fun botoneNB() {
        binding.fragslid2.setOnClickListener {
            findNavController().navigate(R.id.action_slider1Fragment_to_slider2Fragment)
        }
    }
}