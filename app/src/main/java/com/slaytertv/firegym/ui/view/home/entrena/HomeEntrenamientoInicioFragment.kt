package com.slaytertv.firegym.ui.view.home.entrena

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.chip.Chip
import com.slaytertv.firegym.R

import com.slaytertv.firegym.data.model.CalendarioEntrenamientoEntity
import com.slaytertv.firegym.data.model.DiaEntrenamiento
import com.slaytertv.firegym.data.model.Ejercicio
import com.slaytertv.firegym.data.model.ParteCuerpo
import com.slaytertv.firegym.databinding.FragmentHomeEntrenamientoInicioBinding
import com.slaytertv.firegym.ui.view.home.TotalHomeActivity
import com.slaytertv.firegym.util.hide
import com.slaytertv.firegym.util.show
import com.slaytertv.firegym.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeEntrenamientoInicioFragment : Fragment() {
    val TAG:String ="HomeEntrenamientoInicioFragment"
    lateinit var binding: FragmentHomeEntrenamientoInicioBinding
    private val sharedViewModel by activityViewModels<TotalHomeActivity.MySharedViewModel>()
    var actual:Boolean = false
    private var diaAc: DiaEntrenamiento? = null
    private var idEntre:String = ""

    //val viewModelExerciseList: HomeEntreIniViewModel by viewModels()
    val adapterHomeentreinipartecuer by lazy {
        HomeEntreIniParteCuerAdapter(
            onItemClicked = { pos, item ->
                openDialog(item)
            }
        )
    }

    val adapterHomeentreinidias by lazy {
        HomeEntreIniDiasAdapter(
            onItemClicked = { pos, item ->
                adapterHomeentreinipartecuer.updateList(item.partesCuerpo)
                diaAc= item
                if(actual){
                    binding.iniciarejercicio.show()
                }else{
                    binding.iniciarejercicio.hide()
                }
            }
        )
    }

    val adapterHomeentreiniejercicios by lazy {
        HomeEntreIniejerciciAdapter(
            onItemClicked = { pos, item ->
                currentDialog.cancel()
                openDialog2(item)
            }
        )
    }
    private lateinit var currentDialog: Dialog

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

        val staggeredGridLayoutManagerB = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        binding.recyclerpartecuerpo.layoutManager = staggeredGridLayoutManagerB
        binding.recyclerpartecuerpo.adapter = adapterHomeentreinipartecuer

        val staggeredGridLayoutManagerC = StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
        binding.recyclerdias.layoutManager = staggeredGridLayoutManagerC
        binding.recyclerdias.adapter = adapterHomeentreinidias


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
        botones()
    }

    private fun botones() {
        binding.iniciarejercicio.setOnClickListener {
            if(actual){
                openDialog3(diaAc)
            }else{
                toast("no puedes iniciar ejercicio,entrenamiento no habilitado")
            }
        }
    }

    private fun llenardatos(calendarioItem: CalendarioEntrenamientoEntity) {
        binding.nomentrehome.text = calendarioItem.nomrutina
        binding.cantsemanahome.text = calendarioItem.cantsemana
        binding.estadohome.text = calendarioItem.estado
        adapterHomeentreinidias.updateList(calendarioItem.calendarioEntrenamiento)
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
        when(calendarioItem.estado){
            "pendiente" -> actual=false
            "finalizado" -> actual=false
            "actualmente" -> actual=true
        }
        idEntre = calendarioItem.id.toString()

    }
    private fun openDialog(item: ParteCuerpo) {
        adapterHomeentreiniejercicios.updateList(item.ejercicios!!.toMutableList())
        // Los datos están disponibles, puedes mostrar el diálogo
        currentDialog = Dialog(requireContext())
        currentDialog.setContentView(R.layout.exercise_dialog_layout)

        val recyclerView = currentDialog.findViewById<RecyclerView>(R.id.exerciseRecyclerView)
        val staggeredGridLayoutManagerC = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        recyclerView.layoutManager = staggeredGridLayoutManagerC
        recyclerView.adapter =adapterHomeentreiniejercicios
        currentDialog.show()
    }
    private fun openDialog2(item: Ejercicio) {
        val bundle = Bundle().apply {
            putParcelable(ARG_CARD_ITEM, item)
        }
        findNavController().navigate(R.id.action_homeEntrenamientoInicioFragment_to_homeExerciseInicioFragment,bundle)
    }
    private fun openDialog3(item: DiaEntrenamiento?) {
        val bundle = Bundle().apply {
            putParcelable(ARG_CARD_ITEM, item)
            putString("idx",idEntre)
        }
        findNavController().navigate(R.id.action_homeEntrenamientoInicioFragment_to_homeExerciseActualFragment,bundle)
    }
    companion object {
        private const val ARG_CARD_ITEM = "arg_card_item"
    }
}