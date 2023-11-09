package com.slaytertv.firegym.ui.view.ownworkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.slaytertv.firegym.data.model.CalendarioEntrenamientoEntity
import com.slaytertv.firegym.data.model.DiaEntrenamiento
import com.slaytertv.firegym.databinding.FragmentQuestiondosBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestiondosFragment : Fragment() {
    val TAG:String="QuestiondosFragment"
    lateinit var binding: FragmentQuestiondosBinding
    private lateinit var customTableAdapter: CustomTableAdapter
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

    }

    private fun recuperardatos() {
        //val calendario = arguments?.getParcelable<CalendarioEntrenamientoEntity>("calendario")
        val cardItem = arguments?.getParcelable<CalendarioEntrenamientoEntity>(QuestiondosFragment.ARG_Qustion_1)

        if(cardItem != null){
            val i = cardItem.cantsemana.toInt()
            val j = cardItem.dias!!.size
            // Crear una instancia de tu adaptador

            if (i > 0 && j > 0) {
                val headerData = cardItem.dias?.toTypedArray() ?: emptyArray()
                val tabla = Array(i) { Array(j) { DiaEntrenamiento("", "", emptyList()) } }
                val calendarioEntrenamiento = cardItem.calendarioEntrenamiento
                for (row in 0 until i) {
                    for (col in 0 until j) {
                        val index = row * j + col
                        if (index < calendarioEntrenamiento.size) {
                            tabla[row][col] = calendarioEntrenamiento[index]
                        }
                    }
                }
                customTableAdapter = CustomTableAdapter(requireContext(), tabla,headerData)

                // Acceder a tu TableLayout
                val tableLayout = binding.tableLayout2
                tableLayout.removeAllViews()

                // Agregar filas y celdas en la tabla usando el adaptador

                for (row in 0 until i) {
                    val tableRow = customTableAdapter.getView(row, null, tableLayout)
                    tableLayout.addView(tableRow)
                }
                // Agrega la fila de encabezado al TableLayout
                val headerRow = customTableAdapter.getView(0, null, tableLayout)
                tableLayout.addView(headerRow)

            }


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