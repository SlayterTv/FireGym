package com.slaytertv.firegym.ui.view.ownworkout

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import androidx.activity.result.contract.ActivityResultContracts
import com.slaytertv.firegym.MainActivity
import com.slaytertv.firegym.R
import com.slaytertv.firegym.data.model.CalendarioEntrenamientoEntity
import com.slaytertv.firegym.data.model.DiaEntrenamiento
import com.slaytertv.firegym.databinding.FragmentQuestiondosBinding
import com.slaytertv.firegym.util.SharedPrefConstants
import com.slaytertv.firegym.util.toast

class QuestiondosFragment : Fragment() {
    lateinit var binding: FragmentQuestiondosBinding
    private val authLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val mainIntent = Intent(requireContext(), QuestionMyWorkoutActivity::class.java)
            startActivity(mainIntent)
            requireActivity().finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuestiondosBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recuperardatos()
        botoneNB()

    }

    private fun recuperardatos() {
        //val calendario = arguments?.getParcelable<CalendarioEntrenamientoEntity>("calendario")
        val cardItem = arguments?.getParcelable<CalendarioEntrenamientoEntity>(QuestiondosFragment.ARG_Qustion_1)


        // En el primer fragment
        val nuevoCalendario = CalendarioEntrenamientoEntity(
            nomrutina = cardItem!!.nomrutina,
            cantsemana = cardItem.cantsemana,
            dias = cardItem.dias,
            partesDelCuerpo = cardItem.partesDelCuerpo,
            calendarioEntrenamiento = cardItem.calendarioEntrenamiento
        )


        toast(nuevoCalendario.toString())
        // En el segundo fragment (cuando tengas los datos de ejercicios y datosDiarios)
        //nuevoCalendario.ejercicios = listaEjercicios
        //nuevoCalendario.datosDiarios = listaDatosDiarios

        if (cardItem != null) {
            val i = cardItem.cantsemana.toInt()
            val j = cardItem.dias!!.size

            // Verifica que tengas al menos una fila y una columna en la tabla
            if (i > 0 && j > 0) {
                val tabla = Array(i) { Array(j) { DiaEntrenamiento("", "", emptyList()) } }

                // Llena la tabla con los datos de cardItem.calendarioEntrenamiento
                val calendarioEntrenamiento = cardItem.calendarioEntrenamiento
                for (row in 0 until i) {
                    for (col in 0 until j) {
                        val index = row * j + col
                        if (index < calendarioEntrenamiento.size) {
                            tabla[row][col] = calendarioEntrenamiento[index]
                        }
                    }
                }

                // Accede a tu TableLayout
                val tableLayout = binding.tableLayout2
                tableLayout.removeAllViews()

                // Agrega el encabezado (nombres de los días) en una fila adicional
                val headerRow = TableRow(requireContext())
                for (col in 0 until j) {
                    val textView = TextView(requireContext())
                    textView.text = cardItem.dias?.get(col) ?: ""
                    textView.gravity = Gravity.CENTER

                    // Puedes personalizar la apariencia de los TextView según tus necesidades

                    headerRow.addView(textView)
                }
                tableLayout?.addView(headerRow)

                // Crea dinámicamente filas y celdas en la tabla
                for (row in 0 until i) {
                    val tableRow = TableRow(requireContext())

                    for (col in 0 until j) {
                        val diaEntrenamiento = tabla[row][col]

                        val parteCuerpoNombres = diaEntrenamiento.partesCuerpo.map { it.nombre }
                        val nombresConcatenados = parteCuerpoNombres.joinToString("\n").replace(",","\n")


                        val textView = TextView(requireContext())
                        textView.gravity = Gravity.CENTER
                        textView.setPadding(16, 8, 16, 8)
                        textView.setBackgroundResource(R.drawable.table_cell_background)
                        textView.setBackgroundColor(Color.RED)
                        textView.text = "$nombresConcatenados \n"




                        // Puedes personalizar la apariencia de los TextView según tus necesidades
                        // Por ejemplo, puedes establecer márgenes, colores, etc.

                        tableRow.addView(textView)

                        val params = TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT
                        )
                        params.setMargins(10, 10, 10, 10) // Establece los márgenes
                        textView.layoutParams = params
                    }

                    tableLayout.addView(tableRow)
                }
            }
        }






    }

    private fun botoneNB() {

        binding.otro.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            val isFirstRun = sharedPreferences.getBoolean(SharedPrefConstants.SLIDER_INICIAL, true)
            if (isFirstRun) {
                sharedPreferences.edit().putBoolean(SharedPrefConstants.SLIDER_INICIAL, false).apply()
            }
            val authIntent = Intent(requireContext(), MainActivity::class.java)
            authLauncher.launch(authIntent)
        }
    }
    companion object{
        private const val ARG_Qustion_1 = "calendario"
        fun newInstance(cardq1:CalendarioEntrenamientoEntity):QuestiondosFragment{
            val args = Bundle().apply {
                putParcelable(ARG_Qustion_1,cardq1)
            }
            val fragment = QuestiondosFragment()
            fragment.arguments = args
            return fragment
        }
    }
}