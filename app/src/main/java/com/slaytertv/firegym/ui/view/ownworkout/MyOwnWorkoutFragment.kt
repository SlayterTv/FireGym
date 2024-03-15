package com.slaytertv.firegym.ui.view.ownworkout

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.slaytertv.firegym.MainActivity
import com.slaytertv.firegym.data.model.CalendarioEntrenamientoEntity
import com.slaytertv.firegym.databinding.FragmentMyOwnWorkoutBinding
import com.slaytertv.firegym.ui.viewmodel.ownworkout.MyWorkoutViewModel
import com.slaytertv.firegym.util.UiState
import com.slaytertv.firegym.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyOwnWorkoutFragment : Fragment() {
    lateinit var binding: FragmentMyOwnWorkoutBinding
    private val authLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val mainIntent = Intent(requireContext(), MainActivity::class.java)
            startActivity(mainIntent)
            requireActivity().finish()
        }
    }

    private val viewModel: MyWorkoutViewModel by viewModels()
    val adapterentrenamientoList by lazy {
        EntrenamientosListAdapter(
            onItemEdit = { pos, item ->
                dialog("Esta seguro que desea editar el entrenamiento?",2,null,item,null)
            },
            onItemElimina = { pos,item ->
                dialog("Esta seguro de eliminar el entrenamiento?",1,item.id,null,null)
            },
            onItemSeleccion = { pos,item ->
                viewModel.hayCalendarioActual.observe(viewLifecycleOwner){
                    if(it){
                        when(item.estado){
                            "pendiente" -> dialog("ya tiene otro entrenamiento iniciado, seguro que quiere activar este entrenamiento?",5,null,item,"actualmente")
                            "actualmente" -> toast("seleccionado actualmente")
                            "finalizado" -> dialog("se finalizo el entrenamiento, volver a iniciar entrenamiento?",4,null,item,"actualmente")
                        }
                    }else{
                        dialog("Iniciar entrenamiento?",3,null,item,"actualmente")
                    }
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyOwnWorkoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        botones()

        val staggeredGridLayoutManagerB = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        binding.recyclerentrenamientos.layoutManager = staggeredGridLayoutManagerB
        binding.recyclerentrenamientos.adapter = adapterentrenamientoList

        viewModel.getCalendarioWorkout()
        viewModel.verificarCalendarioActual()
    }

    private fun observer() {
        viewModel.myworkoutalllistroom.observe(viewLifecycleOwner){state->
            if(state.isNullOrEmpty()){
                toast("no Existen Workouts creados")
                println(state)
            }else{
                adapterentrenamientoList.updateList(state.toMutableList())
                for(states in state){
                    println(states)
                }
            }
        }
        //delete
        viewModel.deleteWorkout.observe(viewLifecycleOwner){state->
            when(state){
                is UiState.Sucess -> {
                    viewModel.getCalendarioWorkout()
                }
                is UiState.Loading -> {

                }
                is UiState.Failure -> {
                    println(state.error)
                }
                else -> {
                    println("no se elimino el entrenamiento")
                }
            }
        }

        viewModel.updateWorkout.observe(viewLifecycleOwner){state->
            when(state){
                is UiState.Sucess -> {
                    viewModel.getCalendarioWorkout()
                }
                is UiState.Loading -> {

                }
                is UiState.Failure -> {
                    println(state.error)
                }
                else -> {
                    println("no se Actualizo el estado")
                }
            }
        }

        viewModel.actualizarEstados.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Sucess -> {
                    viewModel.getCalendarioWorkout()
                }
                is UiState.Loading -> {

                }
                is UiState.Failure -> {
                    println(state.error)
                }
                else -> {}
            }
        }
        viewModel.reiniciarDatosDiarios.observe(viewLifecycleOwner){ state ->
            when (state) {
                is UiState.Sucess -> {
                    viewModel.getCalendarioWorkout()
                }
                is UiState.Loading -> {

                }
                is UiState.Failure -> {
                    println(state.error)
                }
                else -> {}
            }
        }
    }

    private fun botones() {
        binding.otro.setOnClickListener {
            //crear rutina 0
            //eliminar 1
            //editar2
            //inifin3
            //"finalizado"4
            //pendiente 5

            dialog("Crea tu propia rutina",0,null,null,null)
        }
    }
    fun dialog(msg:String,cuac:Int,item:Long?,itemcompleto:CalendarioEntrenamientoEntity?,estado:String?){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Mensaje")
            .setMessage(msg)
            .setNegativeButton("cancelar") { dialog, which ->

            }
            .setPositiveButton("aceptar") { dialog, which ->
                when(cuac){
                    0 -> {  val authIntent = Intent(requireContext(), QuestionMyWorkoutActivity::class.java)
                        authLauncher.launch(authIntent)}
                    1 -> viewModel.deletetCalendario(item!!.toLong())
                    2 -> toast(itemcompleto.toString())
                    3 -> viewModel.updateCalendarioEstadoById(itemcompleto!!.id.toLong(),estado!!)
                    4 -> {
                        viewModel.reiniciarDatosDiarios(itemcompleto!!.id)
                        //viewModel.actualizarEstadosPendientes()
                        //viewModel.updateCalendarioEstadoById(itemcompleto.id.toLong(),estado!!)
                    }
                    5 -> {
                        viewModel.actualizarEstadosPendientes()
                        viewModel.updateCalendarioEstadoById(itemcompleto!!.id.toLong(),estado!!)
                    }
                }
            }
            .show()
    }

}