package com.slaytertv.firegym.ui.view.home.entrena

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.chip.Chip
import com.slaytertv.firegym.R

import com.slaytertv.firegym.data.model.CalendarioEntrenamientoEntity
import com.slaytertv.firegym.data.model.ParteCuerpo
import com.slaytertv.firegym.databinding.FragmentHomeEntrenamientoInicioBinding
import com.slaytertv.firegym.ui.view.home.TotalHomeActivity
import com.slaytertv.firegym.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeEntrenamientoInicioFragment : Fragment() {
    val TAG:String ="HomeEntrenamientoInicioFragment"
    lateinit var binding: FragmentHomeEntrenamientoInicioBinding
    private val sharedViewModel by activityViewModels<TotalHomeActivity.MySharedViewModel>()


    //val viewModelExerciseList: HomeEntreIniViewModel by viewModels()
    val adapterHomeentreinipartecuer by lazy {
        HomeEntreIniParteCuerAdapter(
            onItemClicked = { pos, item ->
                openDialog(item)
            },
            onCheckBoxClicked = { pos, item ->
                iniciarejercicio(item)
            }
        )
    }

    val adapterHomeentreinidias by lazy {
        HomeEntreIniDiasAdapter(
            onItemClicked = { pos, item ->
                //viewModelExerciseList.getExercisesByCategory(item.tag)
                toast("acacaca $item")
                adapterHomeentreinipartecuer.updateList(item.partesCuerpo)
            }
        )
    }

    val adapterHomeentreiniejercicios by lazy {
        HomeEntreIniejerciciAdapter(
            onItemClicked = { pos, item ->
                //viewModelExerciseList.getExercisesByCategory(item.tag)
                toast("acacaca $item")
                //adapterHomeentreinipartecuer.updateList(item.partesCuerpo)
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

        if(calendarioItem.estado == "pendiente" || calendarioItem.estado == "finalizado" ){

        }else{

        }
    }

    private fun iniciarejercicio(item: ParteCuerpo) {
        toast("222222 $item")
    }

    private fun openDialog(item: ParteCuerpo) {
        toast("1111111 ${item.ejercicios}")
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
}