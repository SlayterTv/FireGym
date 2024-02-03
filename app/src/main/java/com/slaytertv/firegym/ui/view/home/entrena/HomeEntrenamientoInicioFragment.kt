package com.slaytertv.firegym.ui.view.home.entrena

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.slaytertv.firegym.R
import com.slaytertv.firegym.databinding.FragmentHomeEntrenamientoInicioBinding
import com.slaytertv.firegym.ui.view.home.TotalHomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeEntrenamientoInicioFragment : Fragment() {
    lateinit var binding: FragmentHomeEntrenamientoInicioBinding
    private val sharedViewModel by activityViewModels<TotalHomeActivity.MySharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeEntrenamientoInicioBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.calendarioItem?.let { calendarioItem ->
            // Haz algo con calendarioItem en tu fragmento
            println(" me llegan los datos hasta el fragment $calendarioItem")
        }
    }
}