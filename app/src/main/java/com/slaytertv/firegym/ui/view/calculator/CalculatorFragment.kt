package com.slaytertv.firegym.ui.view.calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.slaytertv.firegym.data.model.ExerciseEntity
import com.slaytertv.firegym.databinding.FragmentCalculatorBinding
import com.slaytertv.firegym.ui.viewmodel.exerciselist.ExerciseListViewModel
import com.slaytertv.firegym.ui.viewmodel.splash.ExerciseViewModel
import com.slaytertv.firegym.util.UiState
import com.slaytertv.firegym.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL

@AndroidEntryPoint
class CalculatorFragment : Fragment() {
    lateinit var binding: FragmentCalculatorBinding
    private val viewModel: ExerciseViewModel by viewModels()
    val viewModelExerciseList: ExerciseListViewModel by viewModels()

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
        /*binding.button2.setOnClickListener {
            viewModelExerciseList.getAllExerciseList()
        }
        binding.button.setOnClickListener {
            val exercise = ExerciseEntity(
                category = "Ejercicio",
                link = "https://ejemplo.com",
                foto = ,
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

        viewModelExerciseList.exercisealllist.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Loading -> {
                    //binding.homeProgresbarTop.show()
                }
                is UiState.Failure -> {
                    //binding.homeProgresbarTop.hide()
                    toast(state.error)
                }
                is UiState.Sucess -> {
                    viewLifecycleOwner.lifecycleScope.launch {
                        val cuac = state.data.map { exercise ->
                            /*val fotoBlob = withContext(Dispatchers.IO) {
                                sacarblob(exercise.foto)
                            }*/
                            ExerciseEntity(
                                id = exercise.id.toLong(),
                                category = exercise.category,
                                link = exercise.link,
                                foto = exercise.foto, //fotoBlob,
                                foto2 = exercise.foto2,
                                muscle_group = exercise.muscle_group,
                                spec_group = exercise.spec_group,
                                bar_data = exercise.bar_data,
                                name = exercise.name
                            )
                        }

                        for (cuacx in cuac) {
                            viewModel.insertExercise(cuacx)
                        }
                    }

                }
            }
        }*/

    }
    suspend fun sacarblob(url: String): ByteArray = withContext(Dispatchers.IO) {
        val urlConnection = URL(url).openConnection() as HttpURLConnection
        return@withContext try {
            urlConnection.connect()
            val inputStream = urlConnection.inputStream
            val outputStream = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            outputStream.toByteArray()
        } finally {
            urlConnection.disconnect()
        }
    }

}