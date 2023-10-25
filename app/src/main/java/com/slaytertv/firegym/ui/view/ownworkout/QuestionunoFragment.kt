package com.slaytertv.firegym.ui.view.ownworkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.slaytertv.firegym.R
import com.slaytertv.firegym.databinding.FragmentQuestionunoBinding
import com.slaytertv.firegym.util.toast

class QuestionunoFragment : Fragment() {
    lateinit var binding: FragmentQuestionunoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuestionunoBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        botoneNB()

        val daysOfWeek = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
        for (day in daysOfWeek) {
            val chip = Chip(requireContext())
            chip.text = day
            chip.isCheckable = true
            binding.chipGroup.addView(chip)
        }

        val bodyPart = listOf("neck", "shoulders", "trapezius", "chest", "back", "spinae", "biceps", "triceps", "forearm", "abs", "leg", "calf", "hips", "cardio", "fullbody")
        for (part in bodyPart) {
            val chip = Chip(requireContext())
            chip.text = part
            chip.isCheckable = true
            binding.chipBodypart.addView(chip)
        }
    }

    private fun botoneNB() {
        binding.otro.setOnClickListener {
            //findNavController().navigate(R.id.action_questionunoFragment_to_questiondosFragment)
            verificar()
        }
    }
    fun verificar(){
        val selectedDays = mutableListOf<String>()
        for (i in 0 until binding.chipGroup.childCount) {
            val chip = binding.chipGroup.getChildAt(i) as Chip
            if (chip.isChecked) {
                selectedDays.add(chip.text.toString())
            }
        }

        val selectedParts = mutableListOf<String>()
        for (i in 0 until binding.chipBodypart.childCount) {
            val chip = binding.chipBodypart.getChildAt(i) as Chip
            if (chip.isChecked) {
                selectedParts.add(chip.text.toString())
            }
        }

        if(selectedParts.isEmpty()){
            toast("seleccione al menos una parte del cuerpo para entrenar")
            return
        }
        if(selectedDays.isEmpty()){
            toast("seleccione al menos un dia de la semana para entrenar")
            return
        }
        toast("${selectedDays.toString()} ${selectedParts.toString()}")
        findNavController().navigate(R.id.action_questionunoFragment_to_questiondosFragment)

    }

}