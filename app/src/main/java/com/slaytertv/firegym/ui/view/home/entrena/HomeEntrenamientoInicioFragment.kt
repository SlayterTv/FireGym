package com.slaytertv.firegym.ui.view.home.entrena

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.Chip
import com.slaytertv.firegym.R
import com.slaytertv.firegym.data.model.CalendarioEntrenamientoEntity
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


        val daysOfWeek = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
        for (day in daysOfWeek) {
            val chip = Chip(requireContext())
            chip.text = day
            chip.isCheckable = true
            binding.chipGroup.addView(chip)
        }

        val bodyPart = listOf("neck", "shoulders", "trapezius", "chest", "back", "erector-spinae", "biceps", "triceps", "forearm", "abs", "leg", "calf", "hip", "cardio", "full-body")
        for (part in bodyPart) {
            val chip = Chip(requireContext())
            chip.text = part
            chip.isCheckable = true
            binding.chipBodypart.addView(chip)
        }


        sharedViewModel.calendarioItem?.let { calendarioItem ->
            // Haz algo con calendarioItem en tu fragmento
            println(" me llegan los datos hasta el fragment $calendarioItem")
            llenardatos(calendarioItem)
        }
    }

    private fun llenardatos(calendarioItem: CalendarioEntrenamientoEntity) {
        binding.nomentrehome.text = calendarioItem.nomrutina
        binding.cantsemanahome.text = calendarioItem.cantsemana
        binding.estadohome.text = calendarioItem.estado

        // Resaltar chips en el grupo de días
        for (i in 0 until binding.chipGroup.childCount) {
            val chip = binding.chipGroup.getChildAt(i) as? Chip
            chip?.isChecked = calendarioItem.dias?.contains(chip?.text) == true
        }

        // Resaltar chips en el grupo de partes del cuerpo
        for (i in 0 until binding.chipBodypart.childCount) {
            val chip = binding.chipBodypart.getChildAt(i) as? Chip
            chip?.isChecked = calendarioItem.partesDelCuerpo?.contains(chip?.text) == true
        }

        if(calendarioItem.estado == "pendiente" || calendarioItem.estado == "finalizado" ){

        }else{

        }
    }
}