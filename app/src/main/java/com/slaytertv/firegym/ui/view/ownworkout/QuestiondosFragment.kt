package com.slaytertv.firegym.ui.view.ownworkout

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.slaytertv.firegym.MainActivity
import com.slaytertv.firegym.R
import com.slaytertv.firegym.data.model.CalendarioEntrenamientoEntity
import com.slaytertv.firegym.data.model.DiaEntrenamiento
import com.slaytertv.firegym.data.model.Ejercicio
import com.slaytertv.firegym.databinding.FragmentQuestiondosBinding
import com.slaytertv.firegym.ui.viewmodel.ownworkout.MyWorkoutViewModel
import com.slaytertv.firegym.ui.viewmodel.ownworkout.OwnWorkoutViewModel
import com.slaytertv.firegym.util.SharedPrefConstants
import com.slaytertv.firegym.util.UiState
import com.slaytertv.firegym.util.dialogx
import com.slaytertv.firegym.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestiondosFragment : Fragment() {
    val TAG:String="QuestiondosFragment"
    lateinit var binding: FragmentQuestiondosBinding
    private val authLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val mainIntent = Intent(requireContext(), QuestionMyWorkoutActivity::class.java)
            startActivity(mainIntent)
            requireActivity().finish()
        }
    }
    private val viewModel: MyWorkoutViewModel by viewModels()
    val viewModelExerciseList: OwnWorkoutViewModel by viewModels()
    val adapterexerciseList: QuestionListCategoAdapter = QuestionListCategoAdapter()
    private lateinit var currentDialog: Dialog
    private lateinit var adaptedList: List<Ejercicio>

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
        observer()
        recuperardatos()
        botoneNB()
    }

    private fun recuperardatos() {
        val cardItem = arguments?.getParcelable<CalendarioEntrenamientoEntity>(QuestiondosFragment.ARG_Qustion_1)
        viewModelExerciseList.getAllExercisesByCategoryisselec()

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

                        val index = row * j + col
                        if (index < calendarioEntrenamiento.size) {
                            tabla[row][col] = calendarioEntrenamiento[index]

                            for (parteCuerpo in tabla[row][col].partesCuerpo) {
                                val nombreParteCuerpo = parteCuerpo.nombre

                                val textView = TextView(requireContext())
                                textView.gravity = Gravity.CENTER
                                textView.setPadding(4, 4, 4, 4)
                                textView.setBackgroundResource(R.drawable.table_cell_background)
                                //textView.setBackgroundColor(Color.RED)
                                textView.text = nombreParteCuerpo

                                textView.setOnClickListener {
                                    // Abre el diálogo con los ejercicios para el elemento 'x'
                                    viewModelExerciseList.getExercisesByCategoryisselec(nombreParteCuerpo)
                                }
                                // Puedes personalizar la apariencia de los TextView según tus necesidades
                                val params = TableRow.LayoutParams(
                                    TableRow.LayoutParams.MATCH_PARENT,
                                    TableRow.LayoutParams.WRAP_CONTENT
                                )
                                params.setMargins(4, 4, 4, 4) // Establece los márgenes
                                textView.layoutParams = params

                                // Agrega el TextView a la vista actual (por ejemplo, a tu TableRow o a algún otro contenedor).
                                tableRow.addView(textView)

                            }
                            //println(tabla[row][col])
                            //println(cardItem)
                        }
                    }
                    tableLayout.addView(tableRow)
                }
            }
        }
    }

    private fun observer(){
        viewModelExerciseList.exerciselistroom.observe(viewLifecycleOwner){state ->
            if (!state.isNullOrEmpty()) {
                adapterexerciseList.updateList(state.toMutableList())

                // Los datos están disponibles, puedes mostrar el diálogo
                currentDialog = Dialog(requireContext())
                currentDialog.setContentView(R.layout.exercise_dialog_layout)

                val recyclerView = currentDialog.findViewById<RecyclerView>(R.id.exerciseRecyclerView)
                val staggeredGridLayoutManagerC = StaggeredGridLayoutManager(2, LinearLayoutManager.HORIZONTAL)
                recyclerView.layoutManager = staggeredGridLayoutManagerC
                recyclerView.adapter =adapterexerciseList

                currentDialog.show()
            }
        }

        viewModelExerciseList.exercisealllistroom.observe(viewLifecycleOwner){state ->
            if (!state.isNullOrEmpty()) {
                adaptedList = state.map { originalEjercicio ->
                    // Aquí adaptas cada elemento a la nueva estructura Ejercicio
                    Ejercicio(
                        id = originalEjercicio.id.toString(),
                        tipo = originalEjercicio.category,
                        series = originalEjercicio.serie,
                        repeticiones = originalEjercicio.repeticion,
                        peso = originalEjercicio.peso.toString(),
                        estado = "pendiente",
                        progresion = ""
                    )
                }
                //println(adaptedList)
            }
        }

        viewModel.insertWorkout.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    //handleLoading(isLoading = true)
                }
                is UiState.Failure -> {
                    //handleLoading(isLoading = false)
                    dialogx(state.error)
                }
                is UiState.Sucess -> {
                    toast("Entrenamiento guardado")
                    val sharedPreferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                    val isFirstRun = sharedPreferences.getBoolean(SharedPrefConstants.SLIDER_INICIAL, true)
                        if (isFirstRun) {
                        sharedPreferences.edit().putBoolean(SharedPrefConstants.SLIDER_INICIAL, false).apply()
                    }
                    val authIntent = Intent(requireContext(), MainActivity::class.java)
                    authLauncher.launch(authIntent)
                }
            }
        }
    }

    private fun botoneNB() {
        binding.otro.setOnClickListener {
            val cardItem = arguments?.getParcelable<CalendarioEntrenamientoEntity>(QuestiondosFragment.ARG_Qustion_1)
            // En el primer fragment
            val nuevoCalendario = CalendarioEntrenamientoEntity(
                nomrutina = cardItem!!.nomrutina,
                cantsemana = cardItem.cantsemana,
                dias = cardItem.dias,
                partesDelCuerpo = cardItem.partesDelCuerpo,
                estado = "pendiente",
                calendarioEntrenamiento = cardItem.calendarioEntrenamiento
            )
            val xx = a(nuevoCalendario)
            println("guardae $xx")
            viewModel.insertCalendario(xx)
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

    private fun a(nuevoCalendario:CalendarioEntrenamientoEntity):CalendarioEntrenamientoEntity{
        if (nuevoCalendario != null) {
            val i = nuevoCalendario.cantsemana.toInt()
            val j = nuevoCalendario.dias!!.size
            // Verifica que tengas al menos una fila y una columna en la tabla
            if (i > 0 && j > 0) {
                val tabla = Array(i) { Array(j) { DiaEntrenamiento("", "", emptyList()) } }
                // Llena la tabla con los datos de cardItem.calendarioEntrenamiento
                val calendarioEntrenamiento = nuevoCalendario.calendarioEntrenamiento
                for (row in 0 until i) {
                    for (col in 0 until j) {
                        val index = row * j + col
                        if (index < calendarioEntrenamiento.size) {
                            tabla[row][col] = calendarioEntrenamiento[index]
                            //
                            for (parteCuerpo in tabla[row][col].partesCuerpo) {
                                val nombreParteCuerpo = parteCuerpo.nombre
                                parteCuerpo.ejercicios = obtenerListaEjerciciosPorTipo(nombreParteCuerpo)
                            }
                        }
                    }
                }
            }
        }
        return nuevoCalendario
    }
    private fun obtenerListaEjerciciosPorTipo(tipoDeseado: String): List<Ejercicio> {
        return adaptedList.filter { it.tipo == tipoDeseado }
    }
}