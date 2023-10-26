package com.slaytertv.firegym.ui.view.ownworkout

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
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
        cargartabla(selectedDays,selectedParts)

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
                textView.text = "agregar $i, $j"
                textView.setPadding(16, 8, 16, 8)
                textView.setBackgroundResource(R.drawable.table_cell_background) // Configura el fondo personalizado
                textView.setBackgroundColor(Color.RED)
                dataRow.addView(textView)
                textView.setOnClickListener {
                    showBodyPartSelectionDialog(partescuerpo,cantdia,i,j)
                    //toast("$i, Día $j")
                }

                // Configura márgenes en el TextView de los datos
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