package com.slaytertv.firegym.ui.view.ownworkout

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.slaytertv.firegym.R
import com.slaytertv.firegym.data.model.CalendarioEntrenamientoEntity
import com.slaytertv.firegym.data.model.CalendarioEntrenamientoItem
import com.slaytertv.firegym.data.model.DiaEntrenamiento
import com.slaytertv.firegym.data.model.ParteCuerpo
import com.slaytertv.firegym.databinding.FragmentQuestionunoBinding
import com.slaytertv.firegym.ui.viewmodel.ownworkout.MyWorkoutViewModel
import com.slaytertv.firegym.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionunoFragment : Fragment() {
    lateinit var binding: FragmentQuestionunoBinding
    val viewModel: MyWorkoutViewModel by viewModels()
    var cuacl:Boolean = false

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
        observer()
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
        binding.otro2.setOnClickListener {
            //findNavController().navigate(R.id.action_questionunoFragment_to_questiondosFragment)

           /* val nuevoCalendario = CalendarioEntrenamientoEntity(
                nomrutina = binding.nomrutina.text.toString(),
                cantsemana = binding.cantsema.text.toString(),
                dias = listOf("Lunes", "Martes", "Miércoles"),
                partesDelCuerpo = listOf("Pecho", "Espalda")
            )

            /*val calendarioEntrenamientoEntity = CalendarioEntrenamientoItem(
                calendarioEntrenamiento = listOf(
                    DiaEntrenamiento(
                        dia = "lunes",
                        fecha = "",
                        partesCuerpo = listOf(
                            ParteCuerpo(nombre = "Pecho"),
                            ParteCuerpo(nombre = "Pecho")
                        )
                    )
                )
            )*/

            viewModel.insertCalendario(nuevoCalendario)
            viewModel.getCalendarioWorkout()*/
        }
    }
    private fun observer() {
        viewModel.myworkoutalllistroom.observe(viewLifecycleOwner) { state ->
            if(state.isNotEmpty()){
                //adapterexerciseList.updateList(state.toMutableList())
                toast(state.toString())
            }else{
                toast("no hay entrenamientos agregados")
            }
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
        if(binding.nomrutina.text.toString().isEmpty()){
            toast("agregue un nombre para su rutina")
            return
        }
        if(binding.cantsema.text.toString().isEmpty()){
            toast("agregue la cantidad de semanas")
            return
        }
        toast("${selectedDays.toString()} ${selectedParts.toString()}")
        //findNavController().navigate(R.id.action_questionunoFragment_to_questiondosFragment)
        val listaDiasEntrenamiento = mutableListOf<DiaEntrenamiento>()
        if(cuacl){


            if (areAllCellsNonNull(binding.tableLayout)) {

            } else {
                toast("rellene todos los campos del calendario")
                return
            }

            // Lista para almacenar los días de entrenamiento


// Asegúrate de tener suficientes filas (semanas) y columnas (días de la semana) en tu tabla
            val totalSemanas = binding.tableLayout.childCount
            val totalDiasSemana = selectedDays.size

// Obtiene los nombres de los días de la semana desde la primera fila
            val headerRow = binding.tableLayout.getChildAt(0) as? TableRow
            val nombresDiasSemana = mutableListOf<String>()
            if (headerRow != null) {
                for (j in 0 until totalDiasSemana) {
                    val textView = headerRow.getChildAt(j) as? TextView
                    if (textView != null) {
                        nombresDiasSemana.add(textView.text.toString())
                    }
                }
            }
// Recorre las filas desde la segunda fila en adelante
            for (i in 1 until totalSemanas) {
                val row = binding.tableLayout.getChildAt(i)

                if (row is TableRow) {
                    // Recorre las celdas de partes del cuerpo a partir de la columna 0
                    for (j in 0 until totalDiasSemana) {
                        val textView = row.getChildAt(j) as? TextView
                        if (textView != null) {
                            val parteCuerpoNombre = textView.text.toString()
                            val dia = nombresDiasSemana[j] // Utiliza el nombre del día de la semana
                            val fecha = "" // Agrega tu lógica de fecha aquí

                            val parteCuerpo = ParteCuerpo(parteCuerpoNombre)
                            val diaEntrenamiento = DiaEntrenamiento(dia, fecha, listOf(parteCuerpo))

                            listaDiasEntrenamiento.add(diaEntrenamiento)
                            Log.e("TAG",listaDiasEntrenamiento.toString())
                        }
                    }
                }
            }






        }else{
            cargartabla(selectedDays,selectedParts)
            cuacl = true
            return
        }

        guardartabla(selectedDays,selectedParts,listaDiasEntrenamiento)






    }
    fun guardartabla(
        cantdia: MutableList<String>,
        partescuerpo: MutableList<String>,
        listaDiasEntrenamiento: MutableList<DiaEntrenamiento>
    ){
        val nuevoCalendario = CalendarioEntrenamientoEntity(
            nomrutina = binding.nomrutina.text.toString(),
            cantsemana = binding.cantsema.text.toString(),
            dias = cantdia,
            partesDelCuerpo = partescuerpo,
            calendarioEntrenamiento = listaDiasEntrenamiento
        )




        viewModel.insertCalendario(nuevoCalendario)
        viewModel.getCalendarioWorkout()
    }
    fun areAllCellsNonNull(tableLayout: TableLayout): Boolean {
        for (i in 0 until tableLayout.childCount) {
            val row = tableLayout.getChildAt(i) as? TableRow

            if (row != null) {
                for (j in 0 until row.childCount) {
                    val textView = row.getChildAt(j) as? TextView
                    if (textView?.text.isNullOrBlank()) {
                        return false // Si encuentra una celda con texto nulo o vacío, retorna false
                    }
                }
            }
        }
        return true // Si todas las celdas contienen texto, retorna true
    }

    private fun cargartabla(cantdia:MutableList<String>,partescuerpo:MutableList<String>) {
        val semanas = binding.cantsema.text.toString()
        val diasSemana = cantdia.size

        binding.tableLayout.removeAllViews()
        val headerRow = TableRow(requireContext())
        for (i in 0 until cantdia.size) {
            val headerTextView = TextView(requireContext())
            headerTextView.text = cantdia[i]
            headerRow.addView(headerTextView)

            val params = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(10, 10, 10, 10)
            headerTextView.layoutParams = params
        }
        binding.tableLayout.addView(headerRow)

        for (i in 0 until semanas.toInt()) {
            val dataRow = TableRow(requireContext())
            for (j in 0 until diasSemana) {
                val textView = TextView(requireContext())
                textView.gravity = Gravity.CENTER
                textView.text = ""
                textView.setPadding(16, 8, 16, 8)
                textView.setBackgroundResource(R.drawable.table_cell_background) // Configura el fondo personalizado
                textView.setBackgroundColor(Color.RED)
                dataRow.addView(textView)
                textView.setOnClickListener {
                    showBodyPartSelectionDialog(partescuerpo,cantdia,i,j)
                }
                val params = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(10, 10, 10, 10) // Establece los márgenes
                textView.layoutParams = params
            }
            binding.tableLayout.addView(dataRow)
        }
    }

    private fun showBodyPartSelectionDialog(selectedBodyParts: MutableList<String>,cantdia:MutableList<String>,i:Int,j:Int) {
        val bodyPartList = selectedBodyParts.toTypedArray()
        val selectedBodyPartsx = mutableListOf<String>()
        val checkedItems = BooleanArray(bodyPartList.size)

        AlertDialog.Builder(requireContext())
            .setTitle("Selecciona partes del cuerpo")
            .setMultiChoiceItems(bodyPartList, checkedItems) { _, which, isChecked ->
                if (isChecked) {
                    selectedBodyPartsx.add(bodyPartList[which])
                } else {
                    selectedBodyPartsx.remove(bodyPartList[which])
                }
            }
            .setPositiveButton("Aceptar") { _, _ ->
                // Actualizar la tabla con las partes del cuerpo seleccionadas
                updateTableWithSelectedBodyParts(selectedBodyPartsx,cantdia,i,j)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun updateTableWithSelectedBodyParts(selectedBodyParts: MutableList<String>,cantdia:MutableList<String>,row:Int,column:Int) {
        val tableRow = binding.tableLayout.getChildAt(row + 1) as TableRow // El +1 es para omitir la fila de encabezado
        val cell = tableRow.getChildAt(column) as TextView
        cell.setBackgroundColor(Color.GREEN)
        cell.text = selectedBodyParts.joinToString(", ")
    }


}