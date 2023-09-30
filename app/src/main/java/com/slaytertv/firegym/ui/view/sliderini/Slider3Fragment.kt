package com.slaytertv.firegym.ui.view.sliderini

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.slaytertv.firegym.AuthActivity

import com.slaytertv.firegym.R
import com.slaytertv.firegym.SliderActivity
import com.slaytertv.firegym.databinding.FragmentSlider3Binding
import com.slaytertv.firegym.util.SharedPrefConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Slider3Fragment : Fragment() {
    val TAG:String="Slider3Fragment"
    lateinit var binding: FragmentSlider3Binding
    private val authLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val mainIntent = Intent(requireContext(), SliderActivity::class.java)
            startActivity(mainIntent)
            requireActivity().finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSlider3Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        botoneNB()
    }

    private fun botoneNB() {
        binding.fragslid2.setOnClickListener {
            findNavController().navigate(R.id.action_slider3Fragment_to_slider2Fragment)
        }
        binding.activitauth.setOnClickListener {

            val sharedPreferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isFirstRun = sharedPreferences.getBoolean(SharedPrefConstants.SLIDER_INICIAL, true)
        if (isFirstRun) {
            sharedPreferences.edit().putBoolean(SharedPrefConstants.SLIDER_INICIAL, false).apply()
        }
            val authIntent = Intent(requireContext(), AuthActivity::class.java)
            authLauncher.launch(authIntent)
        }
    }
}