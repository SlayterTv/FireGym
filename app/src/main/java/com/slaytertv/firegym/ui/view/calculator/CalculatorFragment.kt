package com.slaytertv.firegym.ui.view.calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.slaytertv.firegym.data.model.ExerciseEntity
import com.slaytertv.firegym.databinding.FragmentCalculatorBinding
import com.slaytertv.firegym.ui.viewmodel.splash.ExerciseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalculatorFragment : Fragment() {
    lateinit var binding: FragmentCalculatorBinding
    private val viewModel: ExerciseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (this::binding.isInitialized){
            return binding.root
        }else {
            binding = FragmentCalculatorBinding.inflate(layoutInflater)
            return binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            val exercise = ExerciseEntity(
                category = "Ejercicio",
                link = "https://ejemplo.com",
                foto = "",
                foto2 = "",
                name = "",
                id = 0,
                muscle_group = emptyList(),  // Lista de músculos, puedes proporcionar los valores apropiados
                spec_group = emptyList(),    // Lista de grupos específicos, puedes proporcionar los valores apropiados
                bar_data = emptyList()       // Lista de datos de barra, puedes proporcionar los valores apropiados
            )
            viewModel.insertExercise(exercise)

        }
        viewModel.allExercises.observe(viewLifecycleOwner) { exercises ->
            if (exercises.isNotEmpty()) {
                // Procesa los ejercicios según tus necesidades
                Toast.makeText(requireContext(), exercises.toString(), Toast.LENGTH_SHORT).show()
            } else {
                // La lista de ejercicios está vacía
            }


        }

    }

}